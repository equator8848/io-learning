package com.equator.http.file_server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Equator
 * @Date: 2021/4/6 8:48
 **/
@Slf4j
public class HttpFileServer {
    /**
     * 只能访问该目录下的文件以及子目录
     */
    private final static String DEFAULT_URL = "/src/main/java/com/equator/http/file_server/resources";

    public void run(int port, final String url) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            // 将多个消息转换为单一的FullHttpRequest或者Response，因为上一步的解码器会生成多个消息对象
                            channel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));
                            channel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            // chunked分块，主要作用是支持异步发送大的码流（如大文件传输），不占用过多的内存避免OOM
                            channel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            channel.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture future = bootstrap.bind("localhost", port).sync();
            log.info("文件服务器启动，监听端口为 {}", port);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 1998;
        new HttpFileServer().run(port, DEFAULT_URL);
    }
}
