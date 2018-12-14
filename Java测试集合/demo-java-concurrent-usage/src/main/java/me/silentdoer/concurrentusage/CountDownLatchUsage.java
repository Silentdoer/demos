package me.silentdoer.concurrentusage;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Latch有占有、抓住的意思，这里可以理解为获得锁的意思
 *
 * 它的使用场景比如：有三个不相干的任务A，B，C需要执行，然后等这三个不相干的任务都执行后需要执行某个任务F；
 * 那么最简单的就是串行执行，如A->B->C->F（ABC三个任务的顺序可以打乱，但是F必须最后），但是这样显然最终执行F的耗时将很长；
 * 而如果将ABC三个任务放入三个线程或线程池里执行，然后让F等待这样会耗时比较短，但是需要一个机制使得ABC都执行完毕了再执行F，
 * 这种情况就可以用CountDownLatch了；
 *
 * CountDownLatch还有一个作用，就是它不只是可以用来阻塞一个线程，它可以阻塞多个线程，如A线程调用latch.await()，B也调用latch.await()
 * 那么如果countDown()的次数不够时这两个线程都将被阻塞，但是一但够了则两个会一起执行，所以它可以用于压力测试器的实现；
 * 如A在真正发起请求前需要做一些准备，B也是一样。。。，那么这些线程都可以在做好准备后调用countDown()和await()，这样就会等所有的线程都
 * 做好了准备才会所有线程停止await()而发起请求；
 * 这个有点像打仗时A队做准备，B队做准备，C队做准备，然后他们做好准备后都不说话
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 11/13/2018 10:00 AM
 */
public class CountDownLatchUsage {

    public static void main(String[] args) {
        // TODO CountDownLatch的原理很简单，可以理解为latch.countDown()都会对latch的AtomicInteger成员加一，而latch.await则是不断检测这个值是否达到了5，达到则break

        /*CountDownLatch latch = new CountDownLatch(3);
        // TODO 注意线程池默认创建的线程也是非后台线程，所以哪怕主线程结束也会等待它们结束，为了防止这种情况可以自己创建一个ThreadFactory，线程还是普通的线程，只不过放入了一个叫ThreadPoolExecutor的类里管理而已
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 5, 5,
                                                         // SynchronousQueue可以理解为是一个没有容量的但是可以阻塞的队列，所有offer进的都必须立刻由Worker来接收否则就阻塞
                                                         TimeUnit.SECONDS, new SynchronousQueue<>(), r -> {
            Thread result = new Thread(r);
            result.setName(String.join("", "[", "thread-test-", String.valueOf(result.getId()), "]"));
            result.setDaemon(true);
            return result;
        });

        Runnable delegate = () -> {
            System.out.println(Thread.currentThread().getName());
            latch.countDown();
            latch.countDown();
        };
        Instant starts = Instant.now();
        for (int i = 0; i < 2; i++) {
            pool.execute(delegate);
        }
        try {
            //
            System.out.println(latch.await(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant ends = Instant.now();
        System.out.println(Duration.between(starts, ends));*/

        /*CountDownLatch latch = new CountDownLatch(5);
        // 经过测试CountDownLatch的countDown()方法可以是在同一个线程里执行多次，而且此线程还可以是latch.await()的线程；
        latch.countDown();
        latch.countDown();
        latch.countDown();
        latch.countDown();
        latch.countDown();
        // TODO 超过5次的countDown()也是可以的
        latch.countDown();
        try {
            Instant starts = Instant.now();
            latch.await(2, TimeUnit.SECONDS);
            Instant ends = Instant.now();
            // 输出PT0.001S表示0.001秒
            System.out.println(Duration.between(starts, ends));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        /*MyCountDownLatch latch = new MyCountDownLatch(3);
        latch.countDown();
        latch.countDown();
        latch.countDown();
        latch.countDown();
        try {
            Instant starts = Instant.now();
            System.out.println(latch.await(2, TimeUnit.SECONDS));
            Instant ends = Instant.now();
            // 输出PT0.001S表示0.001秒
            System.out.println(Duration.between(starts, ends));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        CountDownLatch latch = new CountDownLatch(3);
        // 经过测试CountDownLatch的countDown()方法可以是在同一个线程里执行多次，而且此线程还可以是latch.await()的线程；
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 3, 3, TimeUnit.SECONDS, new SynchronousQueue<>());
        pool.execute(() -> {
            System.out.println(Thread.currentThread().getId());
            // TODO 做一些准备工作
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static class MyCountDownLatch {

        private AtomicInteger counter;

        /**
         * 用于reset
         */
        private int defaultCount;

        /**
         * 用于判断当前是否已经在await
         */
        private volatile boolean isAwaiting;

        public MyCountDownLatch(int count) {
            this.defaultCount = count;
            this.counter = new AtomicInteger(count);
        }

        public void countDown() {
            this.counter.decrementAndGet();
        }

        public void countDown(int num) {
            this.counter.addAndGet(-num);
        }

        public boolean reset() {
            if (this.isAwaiting) {
                return false;
            } else {
                this.isAwaiting = true;
                this.counter.set(this.defaultCount);
                this.isAwaiting = false;
            }
            return true;
        }

        public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
            if (this.isAwaiting) {
                return false;
            }
            this.isAwaiting = true;
            boolean result = true;
            final long timeoutMills = unit.toMillis(timeout);
            Instant starts = Instant.now();
            do {
                Instant tmp = Instant.now().plusMillis(-timeoutMills);
                if (tmp.isAfter(starts)) {
                    result = false;
                    break;
                } else {
                    // 如果是sleep(<=0)则会直接被忽略，所以不用担心
                    // 这里sleep剩余毫秒数的一半防止太频繁的while循环而耗资源
                    TimeUnit.MILLISECONDS.sleep(Duration.between(tmp, starts).toMillis() / 2);
                }
            } while (this.counter.get() > 0);
            this.isAwaiting = false;
            return result;
        }

        public boolean await() throws InterruptedException {
            if (this.isAwaiting) {
                return false;
            }
            this.isAwaiting = true;
            do {
                //TimeUnit.MILLISECONDS.sleep(1L);
            } while (this.counter.get() > 0);
            this.isAwaiting = false;
            return true;
        }
    }
}
