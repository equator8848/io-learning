package com.equator.echo.msgpack;

import lombok.Data;
import org.msgpack.annotation.Message;

/**
 * @Author: Equator
 * @Date: 2021/4/4 11:07
 **/
@Data
/**
 * MessagePack的Message注解非常重要！！！没有该注解将无法进行数据传输但是程序没有异常抛出
 */
@Message
public class UserInfo {
    private int age;

    private String userName;
}
