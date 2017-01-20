import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author yangxiaochen
 * @date 2017/1/12 12:16
 */
public class BlockQueueTest {

    @Test
    public void testBlockQueue() throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue(1);

        System.out.println(queue.offer(1));

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        queue.put(2);
        System.out.println("ok");
    }

    @Test
    public void testInterrupte() throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue(1);

        Thread thread = new Thread(() -> {


            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("sleep..");
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 捕捉了中断后要保持中断状态
                }
            }


            System.out.println(Thread.currentThread().isInterrupted());
            System.out.println("interrupte!");
        });

        thread.start();


        Thread.sleep(3000);
        thread.interrupt();
        Thread.sleep(3000);
    }

    @Test
    public void testBarrier() throws BrokenBarrierException, InterruptedException, IOException {
        CyclicBarrier barrier = new CyclicBarrier(10 + 1, ()-> {
            System.out.println("barrier end action");
        });

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random()*10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " ok");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end");
            }).start();
        }

        System.out.println("wait..");
        barrier.await();
        System.out.println("barrier out , go for second part:");

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random()*10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " ok");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end");
            }).start();
        }
        System.out.println("wait..");
        barrier.await();
        System.out.println("barrier out");


    }
}
