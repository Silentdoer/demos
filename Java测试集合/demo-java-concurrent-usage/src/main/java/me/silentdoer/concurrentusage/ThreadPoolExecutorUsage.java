package me.silentdoer.concurrentusage;

import java.util.concurrent.*;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 11/15/2018 10:13 AM
 */
public class ThreadPoolExecutorUsage {

    public static void main(String[] args) {
        //region 测试keepAliveTime
        // 如果LinkedBlockingQueue可以不设置最大容量，它默认是Integer.MAX_VALUE，而ArrayBlockingQueue则必须设置最大容量（不然总不能没offer一个都去调整数组）
        // 除非这个容量确实很大，否则都用ArrayBlockingQueue会更快（遍历、offer、poll都更快）
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 3, 2, TimeUnit.SECONDS, new SynchronousQueue<>(), r -> {
            Thread t = new Thread(r);
            t.setName(String.join("", "[", "thd-test-", String.valueOf(t.getId()), "]"));
            t.setDaemon(true);
            return t;
        });

        pool.execute(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 获取线程池的当前线程数
        System.out.println(pool.getPoolSize());
        pool.execute(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 防止线程还没开始执行进程就结束了
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 此时显然两个线程还没有执行完，所以肯定是2
        System.out.println(pool.getPoolSize());
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 此时两个线程执行完了，但是非核心线程的空闲时间只有1.5秒不到两秒，所以还是2
        System.out.println(pool.getPoolSize());
        // 防止线程还没执行完进程就结束了
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 经过3.5秒后非核心线程已经执行完【耗时1秒】且空闲了2.5秒，所以这个时候再看poolSize是1了
        System.out.println(pool.getPoolSize());
        //endregion
    }
}
