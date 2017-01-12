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
            System.out.println(queue.poll());
        }).start();

        queue.put(2);
        System.out.println("ok");

    }
}
