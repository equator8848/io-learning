package com.equator.timeserver.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: Equator
 * @Date: 2021/3/25 18:48
 **/
@Slf4j
public class AsyncTimeServerHandler implements Runnable {
    private AsynchronousServerSocketChannel ass;

    private CountDownLatch latch;

    public AsyncTimeServerHandler(int port) {
        try {
            ass = AsynchronousServerSocketChannel.open();
            ass.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            log.error("AsyncTimeServerHandler init error ...");
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("AsyncTimeServerHandler wait error ...");
        }
    }

    private void doAccept() {
        ass.accept(this, new AcceptCompletionHandler());
    }

    public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {
        @Override
        public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
            attachment.ass.accept(attachment, this);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            result.read(byteBuffer, byteBuffer, new ReadCompletionHandler(result));
        }

        @Override
        public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
            attachment.latch.countDown();
            log.error("AsyncTimeServerHandler error ...");
        }
    }

    /**
     * 读取消息和发送应答
     */
    public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
        private AsynchronousSocketChannel channel;

        public ReadCompletionHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            attachment.flip();
            byte[] bytes = new byte[attachment.remaining()];
            attachment.get(bytes);
            try {
                String body = new String(bytes, "UTF-8");
                log.info("timer server receive order : {}", body);
                String response = "tell me time".equals(body) ? new Date().toString() : "bad order";
                doWrite(response);
            } catch (UnsupportedEncodingException e) {
                log.error("AsyncTimeServerHandler ReadCompletionHandler error ...");
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                this.channel.close();
            } catch (IOException e) {
                log.error("AsyncTimeServerHandler ReadCompletionHandler error ...");
            }
        }

        private void doWrite(String response) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    if (buffer.hasRemaining()) {
                        // 没有发送完毕则继续发送
                        channel.write(buffer, buffer, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer buffer) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        log.error("AsyncTimeServerHandler ReadCompletionHandler error ...");
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        new Thread(new AsyncTimeServerHandler(1998), "AsyncTimeServerHandler").start();
        log.info("AsyncTimeServerHandler start ...");
    }
}
