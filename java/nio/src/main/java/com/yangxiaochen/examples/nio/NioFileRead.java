package com.yangxiaochen.examples.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yangxiaochen
 * @date 16/6/13 下午12:14
 */
public class NioFileRead {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("nio/file/file.md", "r");
        FileChannel channel = randomAccessFile.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);


        int c = channel.read(buffer);
        System.out.println("Read " + c);


        randomAccessFile.close();
    }
}
