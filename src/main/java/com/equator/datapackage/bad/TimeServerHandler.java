package com.equator.datapackage.bad;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Author: Equator
 * @Date: 2021/3/26 15:36
 **/
@Slf4j
public class TimeServerHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // ByteBuf 类似于 NIO 的 byteBuffer
        ByteBuf readBuffer = (ByteBuf) msg;
        byte[] bytes = new byte[readBuffer.readableBytes()];
        readBuffer.readBytes(bytes);
        String body = new String(bytes, "UTF-8").substring(0, bytes.length - System.getProperty("line.separator").length());
        log.info("timer server receive order : {}, counter is {}", body, ++counter);
        String response = "tell me time".equals(body) ? new Date().toString() : "bad order";
        response += System.getProperty("line.separator");
        ByteBuf writeBuffer = Unpooled.copiedBuffer(response.getBytes());
        ctx.writeAndFlush(writeBuffer);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
