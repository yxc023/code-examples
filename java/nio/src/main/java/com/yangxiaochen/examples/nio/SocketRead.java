package com.yangxiaochen.examples.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @author yangxiaochen
 * @date 2017/8/17 17:18
 */
public class SocketRead {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(9191));


        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        new Thread(() -> {
            while (true) {
                try {
                    selector.select();

                    Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                    for (SelectionKey key : selectionKeySet) {
                        if (key.isAcceptable()) {
                            SocketChannel channel = serverSocketChannel.accept();
                            channel.configureBlocking(false);
                            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        }
                        if (key.isReadable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            channel.read(byteBuffer);
                            int size =  byteBuffer.flip().remaining();
                            byte[] bytes = new byte[size];
                            byteBuffer.get(bytes);
                            System.out.println("server: " + new String(bytes));
                        }
                        if (key.isWritable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.wrap("nimenhaoa".getBytes());
                            channel.write(byteBuffer);
                        }

                    }
                    selectionKeySet.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(9191));

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer);
        int remains = byteBuffer.flip().remaining();

        System.out.println(remains);
        byte[] bytes = new byte[remains];
        byteBuffer.get(bytes);
        System.out.println("client: " + new String(bytes));
        System.out.println("client: " + new String(byteBuffer.array()));
        byteBuffer.rewind();
        socketChannel.write(byteBuffer);

    }
}
