package com.equator.http.file_server;

import com.sun.xml.internal.ws.util.StringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;

/**
 * @Author: Equator
 * @Date: 2021/4/6 8:48
 **/
@Slf4j
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /**
     * URL是URI这个抽象概念的具体实现
     */
    private final String url;

    /**
     * 允许的文件名
     */
    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

    /**
     * 不安全的文件名
     */
    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (request.getDecoderResult().isFailure()) {
            // 判断对HTTP请求消息的解码结果
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        if (request.getMethod() != HttpMethod.GET) {
            // 判断请求方法
            sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
        final String uri = request.getUri();
        final String path = buildPath(ctx, uri);
        if (path == null) {
            return;
        }
        File file = new File(path);
        if (file.isHidden() || !file.exists()) {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
                sendListening(ctx, file);
            } else {
                sendRedirect(ctx, uri + "/");
            }
        }
        if (!file.isFile()) {
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        // 获取文件的长度构造成功的应答消息
        long fileLength = raf.length();
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(CONTENT_LENGTH, fileLength);
        // 判断是否是keepAlive，是的话需要在响应头做同样的设置
        if (isKeepAlive(request)) {
            response.headers().set(CONNECTION, "Keep-Alive");
        }
        ctx.write(response);
        // 将文件写入发送缓冲区
        ChannelFuture sendFileFuture = ctx.write(new ChunkedFile(raf, 0, fileLength, 8192), ctx.newProgressivePromise());
        // 添加监听器
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture channelProgressiveFuture, long progress, long total) throws Exception {
                if (total < 0) {
                    // the end of operation is unknown
                    log.info("文件传输 {}", progress);
                } else {
                    log.info("文件传输进度 {}/{}", progress, total);
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture channelProgressiveFuture) throws Exception {
                log.info("文件传输完毕");
            }
        });
        //如果使用chunked编码，最后需要发送一个编码结束的空消息体，将LastHttpContent.EMPTY_LAST_CONTENT发送到缓冲区中，以此表示所有的消息体已经发送完成，同时调用flush方法将发送缓冲区中的消息刷新到SocketChannel中发送
        ChannelFuture lastContentFuture = ctx.
                writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        //如果是非keepAlive的，最后一包消息发送完成后，服务端要主动断开连接
        if (!isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        log.error("文件服务器异常关闭...");
    }

    private static void sendListening(ChannelHandlerContext ctx, File dir) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(CONTENT_TYPE, "text/html;charset=UTF-8");
        //构造返回的html页面内容
        StringBuilder buf = new StringBuilder();
        String dirPath = dir.getPath();
        buf.append("<!DOCTYPE html>\r\n");
        buf.append("<html><head><title>");
        buf.append(dirPath);
        buf.append("目录：");
        buf.append("</title></head><body>\r\n");
        buf.append("<h3>");
        buf.append(dirPath).append("目录：");
        buf.append("</h3>\r\n");
        buf.append("<ul>");
        buf.append("<li>链接：<a href=\"../\">..</a></li>\r\n");
        for (File f : dir.listFiles()) {
            if (f.isHidden() || !f.canRead()) {
                continue;
            }
            String name = f.getName();
            if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
                continue;
            }
            buf.append("<li>链接：<a href=\"");
            buf.append(name);
            buf.append("\">");
            buf.append(name);
            buf.append("</a></li>\r\n");
        }
        buf.append("</ul></body></html>\r\n");
        //分配消息缓冲对象
        ByteBuf buffer = Unpooled.copiedBuffer(buf, CharsetUtil.UTF_8);
        //将缓冲区的内容写入响应对象，并释放缓冲区
        response.content().writeBytes(buffer);
        buffer.release();
        //将响应消息发送到缓冲区并刷新到SocketChannel中
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private String buildPath(ChannelHandlerContext ctx, String uri) {
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            try {
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch (UnsupportedEncodingException e2) {
                // 先使用UTF8解码，失败后使用ISO8859-1解码，失败后直接返回异常
                // 浏览器会对地址栏中的路径进行编码
                sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            }
        }
        if (!uri.startsWith(url) || !uri.startsWith("/")) {
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return null;
        }
        // 将硬编码的文件路径分隔符替换为本地操作系统的文件路径分隔符
        uri = uri.replace('/', File.separatorChar);
        if (uri.contains(File.separator + ".") || uri.contains('.' + File.separator) ||
                uri.startsWith(".") || uri.endsWith(".") || INSECURE_URI.matcher(uri).matches()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return null;
        }
        // 使用当前项目工程目录构造绝对路径
        return System.getProperty("user.dir") + uri;
    }

    /**
     * 判断是否是KeepAlive机制
     */
    private boolean isKeepAlive(FullHttpRequest request) {
        String connection = request.headers().get(CONNECTION);
        if (connection == null || "".equals(connection)) {
            return false;
        }
        return "Keep-Alive".equals(connection);
    }

    /**
     * 重定向（点击目录后的操作）
     */
    private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(LOCATION, newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 发送异常响应
     */
    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/html;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 响应头设置文件类型
     */
    private void setHeaderContentType(HttpResponse response, File file) {
        MimetypesFileTypeMap mimeTypesTypeMap = new MimetypesFileTypeMap();
        response.headers().set(CONTENT_TYPE, mimeTypesTypeMap.getContentType(file));
    }
}
