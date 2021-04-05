package com.equator.echo.msgpack;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Equator
 * @Date: 2021/4/4 10:02
 **/
@Slf4j
public class EchoClient {
    public void connect() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            // 解码器之前增加LengthFieldBasedFrameDecoder用于处理半包消息
                            channel.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                            channel.pipeline().addLast("msgpack decoder", new MsgPackDecoder());
                            // 编码器之前增加LengthFieldPrepender，其会在ByteBuf之前增加2个字节的消息长度字段
                            channel.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
                            channel.pipeline().addLast("msgpack encoder", new MsgPackEncoder());
                            channel.pipeline().addLast(new EchoClientHandler(1000));
                        }
                    });
            // 发起异步连接操作
            ChannelFuture future = bootstrap.connect("localhost", 1998).sync();
            // 等待客户端链路关闭
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient().connect();
    }
}
