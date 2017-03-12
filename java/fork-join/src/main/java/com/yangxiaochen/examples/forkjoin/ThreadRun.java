package com.yangxiaochen.examples.forkjoin;

/**
 * @author yangxiaochen
 * @date 2017/3/12 23:13
 */
public class ThreadRun {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            int i= 10;
            while (i > 0) {
                System.out.println(i--);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 当所有线程只有daemon线程存在时, 虚拟机shutdown
        thread.setDaemon(true);

        thread.start();

    }
}
