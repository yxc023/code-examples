package com.yangxiaochen.example.zookeeper.lock;

import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.RetryForever;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * @author yangxiaochen
 * @date 2016/12/19 18:22
 */
public class ReentryLockWithCurator {

    private String lockPath = "/lock";
    private CuratorFramework client;
    private String path;
    private String nodeName;
    CuratorWatcher watcher;


    public ReentryLockWithCurator(CuratorFramework client, String path) {
        this.client = client;
        this.path = path;

    }

    public void lock() throws Exception {

        int i = 100;
        while (i > 0) {
            try {
                nodeName = client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(lockPath + "/" + path + "/lock", new byte[0]);
                nodeName = nodeName.substring((lockPath + "/" + path + "/").length());
                i--;
                break;
            } catch (ZkNoNodeException e) {
                try {
                    nodeName = client.create().withMode(CreateMode.PERSISTENT).forPath(lockPath + "/" + path);
                } catch (Exception ignore) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(nodeName);

        watcher = event -> {
            System.out.println(event);
            List<String> queue = client.getChildren().forPath(lockPath + "/" + path);
            if (queue.size() == 0) {
                // TODO
            }
            queue.sort(String::compareTo);
            if (queue.get(0).equals(nodeName)) {
                synchronized (ReentryLockWithCurator.this) {
                    System.out.println("notify");
                    ReentryLockWithCurator.this.notify();
                    System.out.println("lock ok");
                }
            } else {
                client.getChildren().usingWatcher(watcher).forPath(lockPath + "/" + path);
            }
        };


        List<String> queue = client.getChildren().forPath(lockPath + "/" + path);

        if (queue.size() == 0) {
            // TODO
        }
        queue.sort(String::compareTo);
        if (queue.get(0).equals(nodeName)) {
            System.out.println("go");

        } else {
            synchronized (this) {
                System.out.println("wait");
                client.getChildren().usingWatcher(watcher).forPath(lockPath + "/" + path);
                this.wait();
                System.out.println("get lock");
            }
        }
    }

    public void release() throws Exception {
        client.delete().forPath(lockPath + "/" + path + "/" + nodeName);
    }

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 3000, 3000, new RetryForever(500));
        client.start();

        ReentryLockWithCurator lock = new ReentryLockWithCurator(client, "resouce1");

        ReentryLockWithCurator lock2 = new ReentryLockWithCurator(client, "resouce1");

        new Thread(() -> {
            try {
                lock2.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("get lock2");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                lock2.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("release lock2");

        }).start();

        System.out.println("to get lock1");
        lock.lock();
        System.out.println("get lock1");

        Thread.sleep(10000);
        lock.release();
    }
}
