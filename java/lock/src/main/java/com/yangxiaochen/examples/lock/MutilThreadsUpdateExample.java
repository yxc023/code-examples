package com.yangxiaochen.examples.lock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangxiaochen
 * @date 16/5/14 下午10:30
 */
public class MutilThreadsUpdateExample {

    public int[] userScores = new int[10];

    private long startTime;

    public void increase(int index) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userScores[index]++;
    }

    public void decrease(int index) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userScores[index]--;
    }

    public static void main(String[] args) throws InterruptedException {
        // 10个线程, 每个循环100次, 每次随机index, 调用increase
        // 10个线程, 每个循环100次, 每次随机index, 调用decrease
        MutilThreadsUpdateExample example = compute1();

        Thread.sleep(5000); // 99.99%的情况下, 5秒内完成计算
        List<Integer> list = new ArrayList();
        for (int userScore : example.userScores) {
            list.add(userScore);
        }
        System.out.println(list);
        System.out.println(list.stream().mapToInt(score -> score).sum()); // 要求此处输出为0
        System.out.println(System.currentTimeMillis() - example.startTime);
    }


    private static MutilThreadsUpdateExample compute1() {
        MutilThreadsUpdateExample example = new MutilThreadsUpdateExample();
        example.startTime = System.currentTimeMillis();
        Object[] lockObjs = new Object[10];
        for (int i = 0; i < lockObjs.length; i++) {
            lockObjs[i] = new Object();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    int index = (int) Math.floor(Math.random() * 10);
                    synchronized (lockObjs[index]) {
                        example.increase(index);
                    }
                }
                System.out.println(Thread.currentThread().getName() + " done " + (System.currentTimeMillis() - example.startTime));
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    int index = (int) Math.floor(Math.random() * 10);
                    synchronized (lockObjs[index]) {
                        example.decrease(index);
                    }
                }
                System.out.println(Thread.currentThread().getName() + " done " + (System.currentTimeMillis() - example.startTime));
            }).start();
        }
        return example;
    }

}
