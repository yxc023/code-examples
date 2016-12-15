package com.yangxiaochen.example.zookeeper;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author yangxiaochen
 * @date 2016/12/15 10:18
 */
public class ZkClient {

    public static void main(String[] args) throws IOException {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 3000, event -> {
            System.out.println(event);
        });
        zk.exists("/zktest", true, (rc, path, ctx, stat) -> {
            System.out.println(rc + " " + path + " " + ctx + " " + stat);
        }, null);

        System.in.read();
    }
}
