package com.equator.timeserver.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: Equator
 * @Date: 2021/3/25 15:32
 **/
@Slf4j
public class MultiplexerTimeServer implements Runnable {
    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stopSign;

    public MultiplexerTimeServer(int port) {
        try {
            // 监听客户端的连接，是所有客户端连接的父管道
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            // 创建多路复用器，在Linux上通过epoll实现
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            log.error("MultiplexerTimeServer init error ...");
        }
    }

    private void stop() {
        this.stopSign = true;
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                // 处理接入请求
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            } else if (key.isReadable()) {
                // 处理读数据
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    log.info("timer server receive order : {}", body);
                    String response = "tell me time".equals(body) ? new Date().toString() : "bad order";
                    handleOutput(sc, response);
                } else if (readBytes < 0) {
                    // 返回值为-1，链路已经关闭，释放资源
                    key.cancel();
                    sc.close();
                }
                // 没有读取到数据，忽略
            }
        }
    }

    private void handleOutput(SocketChannel channel, String response) throws IOException {
        byte[] bytes = response.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        // 可能出现写半包操作
        channel.write(writeBuffer);
    }

    @Override
    public void run() {
        while (!stopSign) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = null;
                    try {
                        key = iterator.next();
                        iterator.remove();
                        handleInput(key);
                    } catch (Exception h) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                        log.error("MultiplexerTimeServer error ...");
                    }

                }
            } catch (IOException e) {
                log.error("MultiplexerTimeServer error ...");
            }
        }
        if (selector != null) {
            try {
                // 关闭selector，其上众多的Channel、Pipe等资源会被自动销毁
                selector.close();
            } catch (IOException e) {
                log.error("MultiplexerTimeServer close error ...");
            }
        }
    }

    public static void main(String[] args) {
        MultiplexerTimeServer multiplexerTimeServer = new MultiplexerTimeServer(1998);
        new Thread(multiplexerTimeServer, "MultiplexerTimeServer").start();
        log.info("MultiplexerTimeServer start ...");
    }
}
