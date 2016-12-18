package com.yangxiaochen.example.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author yangxiaochen
 * @date 2016/12/15 10:25
 */
public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener {

    String znode;

    DataMonitor dm;

    ZooKeeper zk;

    String filename;

    String exec[];

    Process child;

    public Executor(String hostPort, String znode, String filename,
                    String exec[]) throws KeeperException, IOException {
        this.filename = filename;
        this.exec = exec;
        zk = new ZooKeeper(hostPort, 3000, this);
        while (true) {
            System.out.println(zk.getState());
            if (zk.getState().equals(ZooKeeper.States.CONNECTED)) {
                break;
            }
            Thread.yield();
        }


        dm = new DataMonitor(zk, znode, null, this);
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (!dm.dead) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
        dm.process(event);
    }


    @Override
    public void exists(byte[] data) {
        if (data == null) {

        } else {
            System.out.println(new String(data));

        }
    }

    @Override
    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
//        if (args.length < 4) {
//            System.err
//                    .println("USAGE: Executor hostPort znode filename program [args ...]");
//            System.exit(2);
//        }
        String hostPort = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        String znode = "/zktest";
        String filename = "zktest";
        String exec[] = new String[3];
//        System.arraycopy(args, 3, exec, 0, exec.length);
        try {
            new Executor(hostPort, znode, filename, exec).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
