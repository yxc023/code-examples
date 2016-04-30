package com.yangxiaochen.examples.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

/**
 * @author yangxiaochen
 * @date 16/4/14 上午12:17
 */
public class ForkJoinExample {


    public int m1() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1");
        return 1;
    }

    public int m2() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2");
        return 2;
    }

    public int m3() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m3");
        return 3;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        type0();
        //        type1();
        //        type2();
        //        type3();
        //        type4();

    }

    private static void type0() throws InterruptedException {
        ForkJoinExample test = new ForkJoinExample();
        class Holder {
            int a;
            int b;
            int c;
        }
        Holder h = new Holder();

        Thread t1 = new Thread(() -> h.a = test.m1());
        t1.start();

        Thread t2 = new Thread(() -> h.b = test.m2());
        t2.start();

        Thread t3 = new Thread(() -> h.c = test.m3());
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println(h.a + h.b + h.c);
    }

    private static void type4() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);

        ForkJoinExample test = new ForkJoinExample();
        class Holder {
            int a;
            int b;
            int c;
        }
        Holder h = new Holder();

        new Thread(() -> {
            h.a = test.m1();
            semaphore.release();
        }).start();

        new Thread(() -> {
            h.b = test.m2();
            semaphore.release();
        }).start();

        new Thread(() -> {
            h.c = test.m3();
            semaphore.release();
        }).start();


        semaphore.acquire(3);
        System.out.println(h.a + h.b + h.c);
    }

    private static void type3() throws InterruptedException {
        ForkJoinExample test = new ForkJoinExample();
        class Holder {
            int a;
            int b;
            int c;
        }
        Holder h = new Holder();

        new Thread(() -> {
            h.a = test.m1();
            synchronized (test) {
                if (h.a != 0 && h.b != 0 && h.c != 0) {
                    test.notify();
                }
            }
        }).start();

        new Thread(() -> {
            h.b = test.m2();
            synchronized (test) {
                if (h.a != 0 && h.b != 0 && h.c != 0) {
                    test.notify();
                }
            }
        }).start();

        new Thread(() -> {
            h.c = test.m3();
            synchronized (test) {
                if (h.a != 0 && h.b != 0 && h.c != 0) {
                    test.notify();
                }
            }
        }).start();


        synchronized (test) {
            test.wait();
        }

        System.out.println(h.a + h.b + h.c);
    }

    private static void type2() throws InterruptedException, ExecutionException {
        ForkJoinExample test = new ForkJoinExample();

        FutureTask<Integer> f1 = new FutureTask<>(() -> test.m1());
        FutureTask<Integer> f2 = new FutureTask<>(() -> test.m2());
        FutureTask<Integer> f3 = new FutureTask<>(() -> test.m3());

        new Thread(f1).start();
        new Thread(f2).start();
        new Thread(f3).start();

        System.out.println(f1.get() + f2.get() + f3.get());
    }

    private static void type1() throws InterruptedException, ExecutionException {

        ForkJoinExample test = new ForkJoinExample();

        ForkJoinTask<Integer> f1 = ForkJoinTask.adapt(() -> test.m1()).fork();
        ForkJoinTask<Integer> f2 = ForkJoinTask.adapt(() -> test.m2()).fork();
        ForkJoinTask<Integer> f3 = ForkJoinTask.adapt(() -> test.m3()).fork();

        System.out.println(f1.join() + f2.join() + f3.join());
    }
}
