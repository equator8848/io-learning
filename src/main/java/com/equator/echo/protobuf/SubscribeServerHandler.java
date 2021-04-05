package com.equator.echo.protobuf;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Equator
 * @Date: 2021/4/5 16:45
 **/
@Slf4j
/**
 *  标有@Sharable的Handler，代表了他是一个可以被分享的handler，这就是说服务器注册了这个handler后，可以分享给多个客户端使用，如果没有使用该注解，则每次客户端请求时，都必须重新创建一个handler
 */
@ChannelHandler.Sharable
public class SubscribeServerHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private SubscribeRespProto.SubscribeResp buildResp(int reqId) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqId(reqId);
        builder.setRespCode(0);
        builder.setDesc("subscribe successfully...");
        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        log.info("SubscribeServer receive data {}", req);
        ctx.writeAndFlush(buildResp(req.getSubReqId()));
    }
}
