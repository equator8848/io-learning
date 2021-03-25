package com.equator.timeserver.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/**
 * @Author: Equator
 * @Date: 2021/3/25 9:56
 **/
@Slf4j
public class TimeClient {
    public static void main(String[] args) {
        int port = 1998;
        try (Socket socket = new Socket("localhost", port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (true) {
                out.println("tell me time");
                Random random = new Random();
                Thread.sleep(random.nextInt(1500));
                String response = in.readLine();
                log.info("client receive response : {}", response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
