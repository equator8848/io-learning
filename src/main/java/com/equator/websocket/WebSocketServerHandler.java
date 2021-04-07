package com.equator.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;

/**
 * @Author: Equator
 * @Date: 2021/4/7 9:43
 **/
@Slf4j
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handShaker;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            // 传统的HTTP接入
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            // WS接入
            handleWebSocketRequest(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        // 解码失败
        if (request.getDecoderResult().isFailure() || (!"websocket".equals(request.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        // 构造握手响应信息，本机测试
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory("ws://localhost:8888/ws", null, false);
        handShaker = factory.newHandshaker(request);
        if (handShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), request);
        }
    }

    private void handleWebSocketRequest(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判读是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handShaker.close(ctx.channel(), ((CloseWebSocketFrame) frame).retain());
            return;
        }
        // 判断是否是Ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 仅仅支持文本消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame type not supported", frame.getClass().getName()));
        }
        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        log.info("server channel {} receive {}", ctx.channel(), request);
        ctx.channel().write(new TextWebSocketFrame(String.format("接受到请求：%s，当前时间为%s", request, new Date().toString())));
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
        // 返回响应给客户端
        if (response.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        }

        ChannelFuture future = ctx.channel().writeAndFlush(response);
        // 如果是非Keep-Alive模式则关闭连接
        if (!isKeepAlive(request) || response.getStatus().code() != 200) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private boolean isKeepAlive(FullHttpRequest request) {
        String connection = request.headers().get(CONNECTION);
        if (connection == null || "".equals(connection)) {
            return false;
        }
        return "Keep-Alive".equals(connection);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
