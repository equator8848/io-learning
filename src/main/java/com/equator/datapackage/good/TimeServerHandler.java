package com.equator.datapackage.good;

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
        // 直接获取String类型数据
        String body = (String) msg;
        log.info("timer server receive order : {}, counter is {}", body, ++counter);
        String response = "tell me time".equals(body) ? new Date().toString() : "bad order";
        response += System.getProperty("line.separator");
        ByteBuf writeBuffer = Unpooled.copiedBuffer(response.getBytes());
        ctx.write(writeBuffer);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
