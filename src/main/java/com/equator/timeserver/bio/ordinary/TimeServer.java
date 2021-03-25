package com.equator.timeserver.bio.ordinary;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Equator
 * @Date: 2021/3/25 9:08
 **/
@Slf4j
public class TimeServer {
    public static void main(String[] args) {
        int port = 1998;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("time server start ...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (IOException e) {
            log.error("TimeServer error");

        }
    }
}
