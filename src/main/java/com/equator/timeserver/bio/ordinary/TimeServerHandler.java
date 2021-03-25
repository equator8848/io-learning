package com.equator.timeserver.bio.ordinary;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @Author: Equator
 * @Date: 2021/3/25 9:08
 **/
@Slf4j
public class TimeServerHandler implements Runnable {
    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
             PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true)) {
            while (true) {
                String body = in.readLine();
                if (body == null) {
                    break;
                }
                log.info("timer server receive order : {}", body);
                String response = "tell me time".equals(body) ? new Date().toString() : "bad order";
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("TimeServerHandler error");
        }
    }
}
