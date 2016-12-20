package com.yangxiaochen.example.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

import java.io.IOException;

/**
 * @author yangxiaochen
 * @date 2016/12/19 17:32
 */
public class ZkClientSimple {
    private static IZkDataListener listener;

    public static void main(String[] args) throws IOException {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 3000, 3000, new BytesPushThroughSerializer());
        zkClient.readData("/zktest1");
        zkClient.watchForData("/zktest1");
        listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println(dataPath + " ~ " + new String((byte[]) data));
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println(dataPath + " ~ ");
            }
        };
        zkClient.subscribeDataChanges("/zktest1", listener);






        System.in.read();


    }
}
