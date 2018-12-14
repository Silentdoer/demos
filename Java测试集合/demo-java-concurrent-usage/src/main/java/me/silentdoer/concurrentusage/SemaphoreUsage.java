package me.silentdoer.concurrentusage;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO Semaphore有个不严谨的地方【不过也是为了效率，就类似不公平锁一样】，就是A线程哪怕没有acquire到锁，也能release且增加锁数量
 * 比如semaphore的初始锁是3，然后没有任何线程acquire，然后A线程之间release(4)，这会使得semaphore.availablePermits()的值是7
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 11/15/2018 11:28 AM
 */
public class SemaphoreUsage {

    // ThreadGroup类似于线程池，但它远没有线程池强大，所以可以忽略它的存在；
    public static void main(String[] args) {
        // 表示临界区里可以同时存在三个获得锁的线程【Lock是只能有一个线程】
        /*Semaphore semaphore = new Semaphore(3);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, new SynchronousQueue<>(), Executors.defaultThreadFactory());

        Runnable runnable = () -> {
            try {
                // 由于这个方法会导致当前线程挂起，所以可能抛出这个异常
                semaphore.acquire();
                // semaphore.acquire(2);
                // semaphore.acquire(5);
                System.out.println(Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                // semaphore.release(2);
                // semaphore.release(5);
            }
        };

        // 情形：只是acquire(1)
        for (int i = 0; i < 4; i++) {
            // TODO 输出结果，先三个线程输出，过两秒后第四个线程输出；
            // 说明前面三个都获得了锁，直到2秒后有线程执行完释放了锁第四个线程才能acquire成功
            System.out.println(semaphore.availablePermits());
            pool.execute(runnable);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        /*Semaphore semaphore = new Semaphore(3);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, new SynchronousQueue<>(), Executors.defaultThreadFactory());

        Runnable runnable = () -> {
            try {
                // 由于这个方法会导致当前线程挂起，所以可能抛出这个异常
                // semaphore.acquire();
                // semaphore.acquire(2);
                semaphore.acquire(5);
                System.out.println(Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // semaphore.release();
                // semaphore.release(2);
                semaphore.release(5);
            }
        };

        // 情形：只是acquire(5)
        // TODO 直接一个线程都不执行，因为锁只有三个，但是acquire(5)是获得不到的（Java会做处理，获得的资源要么全都获取，要么一个都获取不到）
        for (int i = 0; i < 4; i++) {
            pool.execute(runnable);
        }*/

        /*Semaphore semaphore = new Semaphore(3);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, new SynchronousQueue<>(), Executors.defaultThreadFactory());

        Runnable runnable = () -> {
            try {
                // 由于这个方法会导致当前线程挂起，所以可能抛出这个异常
                // semaphore.acquire();
                semaphore.acquire(2);
                System.out.println(Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // semaphore.release();
                semaphore.release(2);
            }
        };
        // 情形：只是acquire(2)
        for (int i = 0; i < 4; i++) {
            // 这里是4个线程逐个的输出，即因为线程acquire(2)后只剩下1个锁，所以其他的线程无法一次性获得足够多的锁因此一个都不获得从而等待
            pool.execute(runnable);
        }*/

        /*boolean acquired = semaphore.tryAcquire(2);
        if (acquired) {
            System.out.println("当前线程获得了2个锁");
        } else {
            // 比如如果要充分利用某个线程的资源就可以通过这种形式
            System.out.println("当前线程无法一次性获得2个锁，获得锁失败，是否需要执行其他逻辑");
        }*/

        MySemaphore semaphore = new MySemaphore(3);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, new SynchronousQueue<>(), Executors.defaultThreadFactory());

        Runnable runnable = () -> {
            try {
                // 由于这个方法会导致当前线程挂起，所以可能抛出这个异常
                semaphore.acquire();
                // semaphore.acquire(2);
                System.out.println(Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                // semaphore.release(2);
            }
        };
        // 情形：只是acquire(2)
        for (int i = 0; i < 4; i++) {
            // 这里是4个线程逐个的输出，即因为线程acquire(2)后只剩下1个锁，所以其他的线程无法一次性获得足够多的锁因此一个都不获得从而等待
            pool.execute(runnable);
        }
    }

    /**
     * 这里比如有3把锁，这三把锁是等价的，不存在A线程获得哪把锁就要释放哪把锁；
     */
    public static class MySemaphore {

        /**
         * 其实分布式锁里的Redis就充当了这个AtomicInteger的角色
         */
        private AtomicInteger mutexs;

        public MySemaphore(int count) {
            this.mutexs = new AtomicInteger(count);
        }

        public void acquire() throws InterruptedException {
            acquire(1);
        }

        /**
         * 这里都用非公平就可以，公平的意思是比如A线程先acquire，B线程后acquire，但是它们都没有acquire到，
         * 然后C线程释放了锁，这个时候A和B是自由竞争的，而不是一定是先给A后给B，因此叫非公平，
         * 如果要fair那么还需要用队列去记录先后到达的线程，要做更多的判断
         */
        public void acquire(int permits) throws InterruptedException {
            while (mutexs.addAndGet(-permits) < 0) {
                // 发现获得的资源不够，那么收回资源【要么不获得，要么获得所需的全部】
                mutexs.addAndGet(permits);
                TimeUnit.NANOSECONDS.sleep(1L);
            }
        }

        public void release() {
            release(1);
        }

        /**
         * release方法就简单多了，由于Semaphore是哪怕没有获得这个锁的线程也可以release，而且同时增加锁的数量
         *
         * @param permits
         */
        public void release(int permits) {
            this.mutexs.addAndGet(permits);
        }

        public boolean tryAcquire() {
            return tryAcquire(1);
        }

        public boolean tryAcquire(int permits) {
            // 不用考虑tryAcquire时正好有个acquire(2)的把剩下的一个临时占用的情况，不需要这么精细；
            if (this.mutexs.addAndGet(-permits) < 0) {
                this.mutexs.addAndGet(permits);
                return false;
            } else {
                return true;
            }
        }

        public boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException {
            return tryAcquire(1, timeout, unit);
        }

        public boolean tryAcquire(int permits, long timeout, TimeUnit unit) throws InterruptedException {
            long timeoutMills = unit.toMillis(timeout);
            Instant starts = Instant.now();
            while (this.mutexs.addAndGet(-permits) < 0) {
                this.mutexs.addAndGet(permits);
                Instant tmp = Instant.now().plusMillis(-timeoutMills);
                if (tmp.isAfter(starts)) {
                    return false;
                } else {
                    // sleep剩余毫秒数的一半，若sleep的参数小于等于0则直接跳过了
                    TimeUnit.MILLISECONDS.sleep(Duration.between(tmp, starts).toMillis() / 2);
                }
            }
            return true;
        }
    }
}
