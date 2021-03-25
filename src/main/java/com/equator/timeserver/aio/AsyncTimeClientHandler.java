package com.equator.timeserver.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: Equator
 * @Date: 2021/3/25 19:44
 **/
@Slf4j
public class AsyncTimeClientHandler implements CompletionHandler<Void, AsyncTimeClientHandler>, Runnable {
    private AsynchronousSocketChannel asc;

    private CountDownLatch latch;


    public AsyncTimeClientHandler() {
        try {
            this.asc = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            log.error("AsyncTimeClientHandler init error ...");
        }
    }


    @Override
    public void run() {
        latch = new CountDownLatch(1);
        asc.connect(new InetSocketAddress("localhost", 1998), this, this);
        try {
            latch.await();
            asc.close();
        } catch (Exception e) {
            log.error("AsyncTimeClientHandler error ...");
        }
    }

    @Override
    public void completed(Void result, AsyncTimeClientHandler attachment) {
        byte[] bytes = "tell me time".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        asc.write(writeBuffer, writeBuffer,
                new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer wb) {
                        if (wb.hasRemaining()) {
                            asc.write(wb, wb, this);
                        } else {
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            asc.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                                @Override
                                public void completed(Integer result, ByteBuffer rb) {
                                    rb.flip();
                                    byte[] bodyBytes = new byte[rb.remaining()];
                                    rb.get(bodyBytes);
                                    try {
                                        String response = new String(bodyBytes, "UTF-8");
                                        log.info("client receive response : {}", response);
                                    } catch (UnsupportedEncodingException e) {
                                        log.error("AsyncTimeClientHandler error ...");
                                    } finally {
                                        latch.countDown();
                                    }
                                }

                                @Override
                                public void failed(Throwable exc, ByteBuffer rb) {
                                    try {
                                        asc.close();
                                    } catch (IOException e) {
                                        log.error("AsyncTimeClientHandler error ...");
                                    } finally {
                                        latch.countDown();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer buffer) {
                        try {
                            asc.close();
                        } catch (IOException e) {
                            log.error("AsyncTimeClientHandler error ...");
                        } finally {
                            latch.countDown();
                        }
                    }
                });
    }

    @Override
    public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
        try {
            asc.close();
        } catch (IOException e) {
            log.error("AsyncTimeClientHandler error ...");
        } finally {
            latch.countDown();
        }
    }


    public static void main(String[] args) {
        new Thread(new AsyncTimeClientHandler()).start();
    }
}
