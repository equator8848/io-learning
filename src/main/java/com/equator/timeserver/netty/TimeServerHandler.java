package com.equator.timeserver.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 * @Author: Equator
 * @Date: 2021/3/26 15:36
 **/
@Slf4j
public class TimeServerHandler extends ChannelHandlerAdapter {
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
        String body = new String(bytes, "UTF-8");
        log.info("timer server receive order : {}", body);
        String response = "tell me time".equals(body) ? new Date().toString() : "bad order";
        ByteBuf writeBuffer = Unpooled.copiedBuffer(response.getBytes());
        ctx.write(writeBuffer);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
