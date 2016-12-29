package com.yangxiaochen.example.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.RetryForever;

/**
 * @author yangxiaochen
 * @date 2016/12/19 16:20
 */
public class CuratorFrameworkSimple {


    private static CuratorWatcher watcher = null;

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 3000, 3000, new RetryForever(500));
        client.start();
//        watcher = (CuratorWatcher) event -> {
//            System.out.println(event);
//            client.checkExists().usingWatcher(watcher).forPath("/zktest1");
//            throw new Exception("hahah");
//        };
//        client.start();
//        client.checkExists().usingWatcher(watcher).forPath("/zktest1");
//
//
//        String resource = "resource1";
//
//        while (true) {
//            try {
//                System.out.println("create lock");
//                client.create().withMode(CreateMode.EPHEMERAL).forPath("/lock");
//                client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/lock/" + resource + "/lock", "0".getBytes());
//                break;
//
//            } catch (KeeperException.NoNodeException e) {
//                client.create().withMode(CreateMode.EPHEMERAL).forPath("/lock/"+resource);
//            }
//        }

        InterProcessMutex mutex = new InterProcessMutex(client, "/lock/resouce1"); // or any InterProcessLock


        new Thread(() -> {
            try {
                mutex.acquire();
                System.out.println("do in thread");
                Thread.sleep(10000);
                mutex.release();
                System.out.println("release");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
        mutex.acquire();
        mutex.acquire();
        System.out.println("do in main");
        Thread.sleep(10000);

        mutex.release();


        System.out.println("release");
        System.in.read();
        mutex.release();
        System.out.println("release");


        new Thread(() -> {
            try {
                InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, "/lock/resouce1");
                readWriteLock.readLock().acquire();
                System.out.println("read lock" + Thread.currentThread().getName());
                Thread.sleep(10000);
                readWriteLock.readLock().release();

                readWriteLock.writeLock().acquire();
                System.out.println("write lock" + Thread.currentThread().getName());
                Thread.sleep(10000);
                readWriteLock.writeLock().release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, "/lock/resouce1");
                readWriteLock.readLock().acquire();
                System.out.println("read lock" + Thread.currentThread().getName());
                Thread.sleep(10000);
                readWriteLock.readLock().release();

                readWriteLock.writeLock().acquire();
                System.out.println("write lock" + Thread.currentThread().getName());
                Thread.sleep(10000);
                readWriteLock.writeLock().release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, "/lock/resouce1");
                readWriteLock.writeLock().acquire();
                System.out.println("write lock" + Thread.currentThread().getName());
                Thread.sleep(10000);
                readWriteLock.writeLock().release();

                readWriteLock.readLock().acquire();
                System.out.println("read lock" + Thread.currentThread().getName());
                Thread.sleep(10000);
                readWriteLock.readLock().release();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}
