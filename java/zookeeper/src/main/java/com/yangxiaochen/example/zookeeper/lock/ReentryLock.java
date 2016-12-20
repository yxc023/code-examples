package com.yangxiaochen.example.zookeeper.lock;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.apache.zookeeper.CreateMode;

/**
 * @author yangxiaochen
 * @date 2016/12/19 18:22
 */
public class ReentryLock {

    private String lockPath = "/lock";
    private ZkClient client;
    private String path;

    public ReentryLock(ZkClient client, String path) {
        this.client = client;
        this.path = path;
    }

    public void lock() {
        while (true) {
            try {
                client.create(lockPath + "/" + path + "/lock", new byte[0], CreateMode.EPHEMERAL_SEQUENTIAL);
                break;
            } catch (ZkNoNodeException e) {
                client.create(lockPath + "/" + path, new byte[0], CreateMode.CONTAINER);
            }

        }
    }

    public void release() {

    }

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 3000, 3000, new BytesPushThroughSerializer());
        ReentryLock lock = new ReentryLock(zkClient , "resouce1");

        lock.lock();

        Thread.sleep(10000);
        lock.release();
    }
}
