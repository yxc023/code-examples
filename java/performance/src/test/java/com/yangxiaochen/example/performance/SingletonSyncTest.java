package com.yangxiaochen.example.performance;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author yangxiaochen
 * @date 2017/1/26 18:47
 */
public class SingletonSyncTest {
    @Test
    public void test() throws InterruptedException {

        {
            CountDownLatch latch = new CountDownLatch(1100);
            long t = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                new Thread(() -> {
                    Single single = Single.getInstance();
                    latch.countDown();
                }).start();
            }
            Thread.sleep(1000);
            for (int i = 0; i < 1000; i++) {
                new Thread(() -> {
                    Single single = Single.getInstance();
                    latch.countDown();
                }).start();
            }
            latch.await();
            System.out.println(System.currentTimeMillis() - t);
        }
        {
            CountDownLatch latch2 = new CountDownLatch(1100);
            long t2 = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                new Thread(() -> {
                    Single2 single = Single2.getInstance();
                    latch2.countDown();
                }).start();
            }
            Thread.sleep(1000);
            for (int i = 0; i < 1000; i++) {
                new Thread(() -> {
                    Single2 single = Single2.getInstance();
                    latch2.countDown();
                }).start();
            }
            latch2.await();
            System.out.println(System.currentTimeMillis() - t2);
        }

        {
            CountDownLatch latch2 = new CountDownLatch(1100);
            long t2 = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                new Thread(() -> {
//                    System.out.println("to get single3");
                    Single3 single = Single3.getInstance();
//                    System.out.println("get single3");
                    latch2.countDown();
                }).start();
            }
            Thread.sleep(1000);
            for (int i = 0; i < 1000; i++) {
                new Thread(() -> {
                    Single2 single = Single2.getInstance();
                    latch2.countDown();
                }).start();
            }
            latch2.await();
            System.out.println(System.currentTimeMillis() - t2);
        }

    }

    public static class Single {
        private static Single single;

        private Single() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized static Single getInstance() {
            if (single == null) {
                single = new Single();
            }
            return single;
        }
    }

    public static class Single2 {
        private volatile static Single2 single;

        private Single2() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static Single2 getInstance() {
            if (single == null) {
                synchronized (Single2.class) {
                    if (single == null) {
                        single = new Single2();
                    }
                }
            }
            return single;
        }
    }

    public static class Single3 {
        private static class Holder {
            public static final Single3 single = new Single3();
        }

        private Single3() {
            try {
                Thread.sleep(1000);
//                System.out.println("init single3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static Single3 getInstance() {

            return Holder.single;
        }
    }
}


