package com.equator.timeserver.bio.threadpool;

import com.equator.timeserver.bio.ordinary.TimeServerHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Equator
 * @Date: 2021/3/25 9:08
 **/
@Slf4j
public class TimeServer {
    public static class TimeServerHandlerExecutePool {
        private ExecutorService executor;

        public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
            executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize,
                    120L, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(queueSize));
        }

        public void execute(Runnable task) {
            executor.execute(task);
        }
    }

    public static void main(String[] args) {
        int port = 1998;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            TimeServerHandlerExecutePool executePool = new TimeServerHandlerExecutePool(50, 10000);
            log.info("time server start ...");
            while (true) {
                Socket socket = serverSocket.accept();
                executePool.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            log.error("TimeServer error");

        }
    }
}
