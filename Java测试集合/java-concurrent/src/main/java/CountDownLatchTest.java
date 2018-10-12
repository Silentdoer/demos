import org.joda.time.DateTime;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/19/2018 2:18 PM
 */
public class CountDownLatchTest implements Runnable {

    // 可以把这个对象理解为普通对象，而调用latch.await()就类似obj.wait()，不同的是latch.wait()对应的不是notify()而是countDown()，
    // 且必须是执行5次countDown()后才会产生notify()的效果
    private static final CountDownLatch latch = new CountDownLatch(5);

    @Override
    public void run() {
        // TODO 做前置操作（如并发测试时多个线程的客户端socket去连接服务端，成功后才latch.countDown()）
        System.out.println("连接客户端成功" + Thread.currentThread()
                .getName() + "#" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss:SSS",
                                                           Locale.SIMPLIFIED_CHINESE));
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TODO 经过测试latch.countDown()并不会产生类似.lock()的效果（即不会阻塞当前线程）
        latch.countDown();
        // 一个线程里可以多次调用
        //latch.countDown();
        System.out.println("After连接客户端成功" + Thread.currentThread()
                .getName() + "#" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss:SSS",
                                                           Locale.SIMPLIFIED_CHINESE));
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thd = new Thread(new CountDownLatchTest());
        Thread thd2 = new Thread(new CountDownLatchTest());
        Thread thd3 = new Thread(new CountDownLatchTest());
        Thread thd4 = new Thread(new CountDownLatchTest());
        Thread thd5 = new Thread(new CountDownLatchTest());
        thd.start();
        thd2.start();
        thd3.start();
        thd4.start();
        thd5.start();
        latch.await();
        System.out.println("RRRRRRRRRRR");
    }
}