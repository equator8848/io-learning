package com.equator.timeserver.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: Equator
 * @Date: 2021/3/25 16:42
 **/
@Slf4j
public class TimeClient implements Runnable {
    private SocketChannel socketChannel;

    private Selector selector;

    private volatile boolean stopSign;

    public TimeClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doConnect() throws IOException {
        if (socketChannel.connect(new InetSocketAddress("localhost", 1998))) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            // 如果没有直连成功，说明服务器没有返回TCP握手应答消息，但是这不代表连接失败
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel sc) throws IOException {
        byte[] bytes = "tell me time".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        sc.write(writeBuffer);
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel sc = (SocketChannel) key.channel();

            if (key.isConnectable()) {
                // 判断是否连接成功
                if (sc.finishConnect()) {
                    sc.register(selector, SelectionKey.OP_READ);
                    doWrite(sc);
                } else {
                    System.exit(-1);
                }
            } else if (key.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String response = new String(bytes, "UTF-8");
                    log.info("client receive response : {}", response);
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            log.error("connect error ...");
        }
        while (!stopSign) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (Exception h) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                log.error("TimeClient error ...");
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                log.error("TimeClient close error ...");
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new TimeClient(), "TimeClient").start();
    }
}
