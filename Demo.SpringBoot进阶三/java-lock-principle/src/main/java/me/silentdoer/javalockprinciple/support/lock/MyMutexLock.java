package me.silentdoer.javalockprinciple.support.lock;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;

/**
 * 简单的模拟获取互斥锁机制，全程没用用到synchronized和JDK的Lock，但是要依赖一个能够实现addAndGet和decrementAndGet原子操作的对象
 *
 * TODO 还可以设计为可重入锁，以及用Lock接口抽象实现
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/12/2018 3:54 PM
 */
public class MyMutexLock {
    /**
     * JDK8里替代AtomicLong，默认值是0，但是这个类没有addAndGet之类的方法，add后还必须再调用get来获得值，
     * 这样就不能保证addAndGet是一个原子操作，因此舍弃
      */
    /*private LongAdder lock = new LongAdder();*/
            // volatile int lock; 无法实现这个功能，因为lock++;是两个操作，无法满足lock + 1;赋值lock;获取lock;这三步是原子操作
    private AtomicInteger lock = new AtomicInteger(0);
    private Long lockHolder = null;
    /**
     * 可重入的实现，如果releaseLock时如果要将lockHolder重置需要此值为0，同一个线程每次lock此值都加一
     */
    private volatile int holderLockCount = 0;

    /**
     * 对于分布式锁，其实就是一个锁分发器，由多个 进程 去获取（而JDK的锁则是多个线程去获取，同一进程），
     * 当A进程去一个锁分发器里获得锁，然后锁分发器第一步是记录（如某个值减一）并且同时将减一的值返回给用户（这两步是原子操作中间不夹杂
     * 其他操作）
     */
    public boolean tryLock() {

        // 如果锁存在持有者，那么直接返回false
        if (Objects.nonNull(this.lockHolder)) {
            return false;
        }

        long flagVal = this.lock.longValue();
        // 加这一步是为了下面的有等待时间的tryLock减少后续this.lock.decrementAndGet()的消耗
        if (flagVal < 0) {
            return false;
        }

        // 获得锁，然后当前的锁数量减一；尽管有上面的判断，这一步仍然不能少，否则可能存在两个线程都执行到这一步的情况，如果没有后续判断
        // 就GG
        flagVal = this.lock.decrementAndGet();
        // 成立则说明当前线程this.lock.decrementAndGet() TODO 之前 已经有 其他线程执行了该操作
        if (flagVal < -1) {
            // TODO 这里可以有多种逻辑，1）等待其他线程释放锁；2）直接退出；
            // 这里直接退出，因为外部调用tryLock()如果发现是false是可以while循环来获取的
            // （当然也可能存在即便循环也可能很不幸总是获取不到）
            // return 之前要先加一，否则接下来的线程将永远无法获得锁
            this.lock.incrementAndGet();
            return false;
        }
        // 记录锁持有者
        lockHolder = Thread.currentThread().getId();
        return true;
    }

    /**
     * 获取锁，如果当前获取不到则自旋
     * @param timeout 等待的时间，如果是-1表示一直等待直到获得锁
     * @param unit 时间单位
     */
    public boolean tryLock(long timeout, TimeUnit unit) {
        int flagVal = this.lock.decrementAndGet();
        System.err.println(Thread.currentThread().getName() + "#" + flagVal);

        // flagVal < -1但是this.lockHolder是null是正常的，可能还没到最后的记录操作那步
        if (flagVal < -1) {
            // 因为下面的tryLock()会再一次decrementAndGet()
            this.lock.incrementAndGet();
            System.err.println("11111" + Thread.currentThread().getName());
            Instant timestamp = Instant.now().plusMillis(unit.toMillis(timeout));

            // 自旋锁，即类似while循环
            while(true) {
                boolean locked = this.tryLock();
                if (locked) {
                    System.err.println("222" + Thread.currentThread().getName());
                    return true;
                }
                // 一直等待
                if (timeout < 0) {
                    System.err.println("333" + Thread.currentThread().getName());
                    continue;
                }
                // 规定两次尝试之间至少等待一毫秒
                try {
                    Thread.sleep(1L);
                } catch (Exception e) {
                    System.err.println("444" + Thread.currentThread().getName());
                    return false;
                }
                // 验证是否超时
                Instant now = Instant.now();
                if (now.isAfter(timestamp)) {
                    System.err.println("555" + Thread.currentThread().getName());
                    return false;
                }
            }
        }
        System.err.println("666" + Thread.currentThread().getName());
        // 记录锁持有者
        lockHolder = Thread.currentThread().getId();
        return true;
    }

    /**
     * 这里是有问题的，即必须要求使用者很小心，必须要先tryLock且成功后面才能releaseLock；这里的锁是互斥锁，只有一把锁
     * TODO 如果说要实现，tryLock()成功，那么releaseLock也必须是tryLock成功的线程去执行，那么这里是需要进行线程记录的；
     */
    public boolean releaseLock() throws IllegalAccessException {

        if (Objects.isNull(this.lockHolder)) {
            throw new IllegalAccessException("无法释放无持有者的锁，请先成功加锁");
        }
        long id = Thread.currentThread().getId();
        if (this.lockHolder != id) {
            throw new IllegalAccessException("您并非锁的持有者，无法释放锁");
        }

        // 这里很重要的一点是：当前线程执行下面的api，那么存在一个即刻值，然后将这个即刻值加一，然后获得这个即刻值，这些是原子操作
        // 因此，假设当前线程执行时即刻值>=0，那么显然大于0的值里肯定有一个是当前线程调用releaseLock导致的，
        // 因此需要的decrementAndGet
        int flagVal = this.lock.incrementAndGet();
        // 进行简单的逻辑验证，即归还锁最多只能是0，TODO 有了持有者判定后这一步其实可以不用了
        if (flagVal > 0) {
            this.lock.decrementAndGet();
            return false;
        }
        // 释放持有者，注意，即便赋值为null后有线程正好tryLock，然后导致return true之前执行了其他逻辑也没有关系，
        // 因为当前线程已经执行完逻辑
        this.lockHolder = null;
        return true;
    }

    /**
     * 强制释放锁，可能存在用户在获得锁后忘了releaseLock的情况，但是强制释放锁必须是已经不存在锁持有者线程的前提下
     */
    public boolean forceReleaseLock() {

        if (Objects.isNull(this.lockHolder)) {
            return true;
        }
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 当不存在对应ID的active线程时返回null
        ThreadInfo threadInfo = threadMXBean.getThreadInfo(this.lockHolder);

        // 线程仍然活动不能强制释放锁
        if (Objects.nonNull(threadInfo)) {
            return false;
        }

        // 释放锁
        int flagVal = this.lock.incrementAndGet();
        this.lockHolder = null;
        if (flagVal > 0) {
            throw new Error("致命未知错误，查下什么情况下会导致锁被释放两次");
        }
        return true;
    }
}
