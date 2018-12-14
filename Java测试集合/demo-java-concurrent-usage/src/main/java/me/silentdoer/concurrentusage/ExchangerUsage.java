package me.silentdoer.concurrentusage;

import java.util.concurrent.Exchanger;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 这个类是用来交换 两个 线程的同类数据，A线程调用exchanger.exchange(dataA)会阻塞A线程，直到另一个线程B调用exchanger.exchange(dataB)
 * 那么exchange这个方法就会将dataB返回给线程A，将dataA返回给线程B；
 * 注意：只支持两个线程 的同类型 的数据的交换（如果是三个线程以上就不知道怎么交换了，除非还可以定制交换策略）；
 * 也可以设定等待的时间，如果另一个线程迟迟不给出要交换的数据那么抛出：TimeoutException
 *
 * 业务场景还不是很清楚，感觉用处不大
 * @author liqi.wang
 * @version 1.0.0
 * @date 11/15/2018 4:15 PM
 */
public class ExchangerUsage {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 3L, TimeUnit.SECONDS, new SynchronousQueue<>());
        Exchanger<String> exchanger = new Exchanger<>();

        pool.execute(() -> {
            String dataA = "线程A的数据";
            System.out.printf("当前线程:%s, 数据:%s", Thread.currentThread().getId(), dataA).println();
            try {
                // 这个方法是可能需要等待另一个要通过这个exchanger对象来交换数据的线程执行这个方法来交换，所以可能令当前线程等待，因此需要捕获InterruptedException
                String exchangedData = exchanger.exchange(dataA);
                System.out.printf("当前线程:%s, 交换后的数据:%s", Thread.currentThread().getId(), exchangedData).println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        pool.execute(() -> {
            String dataB = "线程B的数据";
            System.out.printf("当前线程:%s, 数据:%s", Thread.currentThread().getId(), dataB).println();
            try {
                // 线程B sleep也会导致线程A sleep，因为A需要等待B执行exchanger.exchange(..)
                TimeUnit.SECONDS.sleep(2L);
                // 这个方法是可能需要等待另一个要通过这个exchanger对象来交换数据的线程执行这个方法来交换，所以可能令当前线程等待，因此需要捕获InterruptedException
                String exchangedData = exchanger.exchange(dataB);
                System.out.printf("当前线程:%s, 交换后的数据:%s", Thread.currentThread().getId(), exchangedData).println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
