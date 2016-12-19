package com.yangxiaochen.example.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.RetryForever;

/**
 * @author yangxiaochen
 * @date 2016/12/19 16:20
 */
public class CuratorFrameworkSimple {


    private static CuratorWatcher watcher = null;

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 3000, 3000, new RetryForever(500));

        watcher = (CuratorWatcher) event -> {
            System.out.println(event);
            client.checkExists().usingWatcher(watcher).forPath("/zktest1");
            throw new Exception("hahah");
        };
        client.start();
        client.checkExists().usingWatcher(watcher).forPath("/zktest1");



        System.in.read();
    }
}
