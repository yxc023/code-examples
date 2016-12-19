package com.yangxiaochen.example.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author yangxiaochen
 * @date 2016/12/15 10:18
 */
public class ZkClient {

    ZooKeeper zk;


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String znode = "/zktest1";
        ZkClient zkClient = new ZkClient();
        zkClient.zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 3000, event -> {
            System.out.println(event);

            if (event.getType() == Watcher.Event.EventType.None) {
                // We are are being told that the state of the
                // connection has changed
                switch (event.getState()) {
                    case SyncConnected:
                        // In this particular example we don't need to do anything
                        // here - watches are automatically re-registered with
                        // server and any watches triggered while the client was
                        // disconnected will be delivered (in order of course)
                        break;
                    case Expired:
                        /** The serving cluster has expired this session. The ZooKeeper
                         * client connection (the session) is no longer valid. You must
                         * create a new client connection (instantiate a new ZooKeeper
                         * instance) if you with to access the ensemble. */
                        return;

                }
            } else {
                try {
                    zkClient.zk.exists(znode, true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        while (true) {
            if (zkClient.zk.getState().equals(ZooKeeper.States.CONNECTED)) {
                break;
            }
            Thread.yield();
        }
        zkClient.zk.exists(znode, true);
        zkClient.zk.register((event) -> {
            System.out.println("i'm the watch2~ " + event);
        });


        System.in.read();
    }
}
