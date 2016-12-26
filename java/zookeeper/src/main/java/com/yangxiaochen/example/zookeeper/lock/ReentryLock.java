package com.yangxiaochen.example.zookeeper.lock;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * @author yangxiaochen
 * @date 2016/12/19 18:22
 */
public class ReentryLock {

    private String lockPath = "/lock";
    private ZkClient client;
    private String path;
    private String nodeName;


    public ReentryLock(ZkClient client, String path) {
        this.client = client;
        this.path = path;
    }

    public void lock() throws InterruptedException {
        int i = 100;
//        String nodeName = null;
        while (i > 0) {
            try {
                nodeName = client.create(lockPath + "/" + path + "/lock", new byte[0], CreateMode.EPHEMERAL_SEQUENTIAL);
                nodeName = nodeName.substring((lockPath + "/" + path + "/").length());
                i--;
                break;
            } catch (ZkNoNodeException e) {
                try {
                    client.create(lockPath + "/" + path, new byte[0], CreateMode.PERSISTENT);
                } catch (Exception ignore) {

                }
            }
        }

        System.out.println(nodeName);



        client.subscribeChildChanges(lockPath + "/" + path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                List<String> queue = client.getChildren(lockPath + "/" + path);
                if (queue.size() == 0) {
                    // TODO
                }
                queue.sort(String::compareTo);
                if (queue.get(0).equals(nodeName)) {
                    synchronized (ReentryLock.this) {
                        System.out.println("notify");
                        ReentryLock.this.notify();
                    }
                }
            }
        });

        List<String> queue = client.getChildren(lockPath + "/" + path);

        if (queue.size() == 0) {
            // TODO
        }
        queue.sort(String::compareTo);
        if (queue.get(0).equals(nodeName)) {
            System.out.println("go");

        } else {
            synchronized (this) {
                System.out.println("wait");
                this.wait();

                System.out.println("gogo");
            }
        }
    }

    public void release() {
        client.delete(lockPath + "/" + path + "/" + nodeName);
    }

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 3000, 3000, new BytesPushThroughSerializer());
        ReentryLock lock = new ReentryLock(zkClient , "resouce1");

        ReentryLock lock2 = new ReentryLock(zkClient , "resouce1");

        new Thread(() -> {
            try {
                lock2.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("get lock2");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock2.release();
            System.out.println("release lock2");

        }).start();

        lock.lock();
        System.out.println("get lock");

        Thread.sleep(10000);
        lock.release();
    }
}
