package com.equator.echo.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Equator
 * @Date: 2021/4/5 16:24
 **/
@Slf4j
public class SubscribeReqProtoTest {
    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(1);
        builder.setUserName("leo");
        builder.setProductName("Hello Netty");
        List<String> addresses = new ArrayList<>();
        addresses.add("beijing");
        addresses.add("chongqing");
        addresses.add("guangdong");
        builder.addAllAddress(addresses);
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        log.info("before encode {}", req);
        SubscribeReqProto.SubscribeReq other = decode(encode(req));
        log.info("after encode and decode {}", other);
        log.info("is equal {}", req.equals(other));
    }
}
