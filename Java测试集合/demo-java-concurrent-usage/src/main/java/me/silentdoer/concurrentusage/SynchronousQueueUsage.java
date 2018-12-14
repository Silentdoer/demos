package me.silentdoer.concurrentusage;

import java.util.Date;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 11/15/2018 4:46 PM
 */
public class SynchronousQueueUsage {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MINUTES, new SynchronousQueue<>(), r -> {
            Thread result = new Thread(r);
            result.setName(String.join("", "[", "thread-test-", String.valueOf(result.getId()), "]"));
            result.setDaemon(true);
            return result;
        });

        pool.execute(() -> {
            try {
                System.out.println(Thread.currentThread().getId() + "##" + new Date());
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TODO 这里因为拒绝而抛出了异常，说明SynchronousQueue确实没有容量，否则第二个task是能存入SynchronousQueue里的
        pool.execute(() -> {
            try {
                System.out.println(Thread.currentThread().getId() + "##" + new Date());
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
