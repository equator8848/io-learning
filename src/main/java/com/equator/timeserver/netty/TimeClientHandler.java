package com.equator.timeserver.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Equator
 * @Date: 2021/3/26 16:15
 **/
@Slf4j
public class TimeClientHandler extends ChannelHandlerAdapter {
    private final ByteBuf msg;

    public TimeClientHandler() {
        byte[] bytes = "tell me time".getBytes();
        msg = Unpooled.buffer(bytes.length);
        msg.writeBytes(bytes);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TCP链路连接建立完毕之后，此方法被调用
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 服务端返回应答消息时，此方法被调用
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String response = new String(bytes, "UTF-8");
        log.info("client receive response : {}", response);
    }
}
