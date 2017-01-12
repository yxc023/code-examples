import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author yangxiaochen
 * @date 2017/1/12 18:07
 */
public class ParkUnparkTest {

    @Test
    public void test() throws InterruptedException {

        Thread thread = new Thread(() -> {
            LockSupport.park();
            System.out.println("block.");
        });
        thread.start();

        Thread.sleep(2000);
        System.out.println(thread.isAlive());
        for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
            System.out.println(stackTraceElement.toString());
        }
        LockSupport.unpark(thread);


    }

    @Test
    public void test2() {
        Thread thread = Thread.currentThread();
        LockSupport.unpark(thread);//释放许可
        LockSupport.park();// 获取许可
        System.out.println("b");
    }
}
