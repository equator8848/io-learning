package com.equator.echo.msgpack;

import lombok.Data;
import org.msgpack.annotation.Message;

/**
 * @Author: Equator
 * @Date: 2021/4/4 11:07
 **/
@Data
@Message
public class UserInfo {
    private int age;

    private String userName;

    private int userId;
}
