package com.yangxiaochen.example.zookeeper.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.RetryForever;

/**
 * @author yangxiaochen
 * @date 2016/12/28 12:20
 */
public class ReadWriteLockExample {

    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 3000, 3000, new RetryForever(500));
        client.start();

        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, "/lock/resouce1");
//        readWriteLock.readLock()
    }
}
