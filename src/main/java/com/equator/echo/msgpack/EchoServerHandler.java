package com.equator.echo.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
        List<Object> list = (List<Object>) msg;
        for (Object o : list) {
            log.info("EchoServer receive {}", o);
        }
        ctx.writeAndFlush(list);
    }
}
