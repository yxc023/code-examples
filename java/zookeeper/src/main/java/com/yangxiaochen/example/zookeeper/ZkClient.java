package com.yangxiaochen.example.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author yangxiaochen
 * @date 2016/12/15 10:18
 */
public class ZkClient {

    ZooKeeper zk;


    public static void main(String[] args) throws IOException {

        ZkClient zkClient = new ZkClient();
        zkClient.zk = new ZooKeeper("127.0.0.1:2181", 3000, event -> {
            System.out.println(event);
            try {
               zkClient.zk.exists("/zktest1",true);
//                System.out.println(new String(data));
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });



//        AsyncCallback.StatCallback callback = new AsyncCallback.StatCallback() {
//            @Override
//            public void processResult(int rc, String path, Object ctx, Stat stat) {
////                zk.exists("/zktest", true, this, null);
//                if (rc == KeeperException.Code.Ok) {
//                    try {
//                        byte[] data = zk.getData("/zktest",true,null);
//                        System.out.println(new String(data));
//                    } catch (KeeperException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                System.out.println(rc + " " + path + " " + ctx + " " + stat);
////                zk.exists("/zktest", true, (r,p,c,s)->{
////                    System.out.println(r + " " + p + " " + c + " " + s);
////                }, null);
//            }
//        };


//        zk.exists("/zktest", true, callback, null);

        System.in.read();
    }
}
