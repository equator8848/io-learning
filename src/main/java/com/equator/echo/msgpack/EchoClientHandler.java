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

    private final String req = "Hello, Netty ...$_$";

    public EchoClientHandler(int sendTimes) {
        this.sendTimes = sendTimes;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private UserInfo[] buildUserInfo() {
        UserInfo[] userInfos = new UserInfo[sendTimes];
        for (int i = 0; i < sendTimes; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName("leo" + i);
            userInfo.setAge(i + 18);
            userInfos[i] = userInfo;
        }
        return userInfos;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] userInfos = buildUserInfo();
        for (UserInfo userInfo : userInfos) {
            ctx.write(userInfo);
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("Echo Client receive {}, times is {}", msg, ++counter);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
