package com.equator.echo.delimiter_based_frame_decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Equator
 * @Date: 2021/4/3 20:48
 **/
@Slf4j
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 发生异常则关闭链路
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        log.info("server receive {}, times is {}", body, ++counter);
        // 加上分隔符
        body += "$_$";
        ByteBuf response = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(response);
    }
}
