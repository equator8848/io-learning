package com.equator.echo.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @Author: Equator
 * @Date: 2021/4/4 10:44
 **/

public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 从数据报中获取需要解码的byte数组
        final int len = byteBuf.readableBytes();
        final byte[] array = new byte[len];
        byteBuf.getBytes(byteBuf.readerIndex(), array, 0, len);
        // 调用MessagePack的read方法将其反序列化为Object对象，将解码后的对象加入解码列表中
        MessagePack messagePack = new MessagePack();
        list.add(messagePack.read(array));
    }
}
