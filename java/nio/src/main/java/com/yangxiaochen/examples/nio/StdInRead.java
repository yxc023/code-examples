package com.yangxiaochen.examples.nio;

import java.io.IOException;
import java.nio.channels.Pipe;

/**
 * @author yangxiaochen
 * @date 2017/8/17 17:11
 */
public class StdInRead {

    public static void main(String[] args) throws IOException {
        Pipe pipe = Pipe.open();
    }
}
