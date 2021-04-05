package com.equator.echo.msgpack;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Equator
 * @Date: 2021/4/4 9:51
 **/
@Slf4j
public class EchoClientHandler extends ChannelHandlerAdapter {
    private int counter;

    private int sendTimes;

    public EchoClientHandler(int sendTimes) {
        this.sendTimes = sendTimes;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private UserInfo[] buildUserInfo() {
        UserInfo[] userInfoList = new UserInfo[sendTimes];
        for (int i = 0; i < sendTimes; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName("leo" + i);
            userInfo.setAge(i + 18);
            userInfoList[i] = userInfo;
        }
        return userInfoList;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] userInfoList = buildUserInfo();
        /**
         * 对list里面的元素进行逐个发送，对面也是逐个接收。反之则一并接收
         for (UserInfo userInfo : userInfoList) {
         ctx.write(userInfo);
         log.debug("client try write {}", userInfo);
         }
         ctx.flush();
         **/
        ctx.writeAndFlush(userInfoList);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("Echo Client receive {}, times is {}", msg, ++counter);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
