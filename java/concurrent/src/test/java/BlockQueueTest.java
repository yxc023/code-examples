import org.junit.Test;

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
}
