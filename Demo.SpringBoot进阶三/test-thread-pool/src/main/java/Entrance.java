import java.text.MessageFormat;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/9/2018 3:31 PM
 */
public class Entrance {
    public static void main(String[] args) {
        /*
        LinkedBlockingQueue在构造方法里可以配置capacity，如果不配置那么就是Integer的最大值，当offer到饱和状态时，queue将会拒绝（拒绝策略根据用的不同的api而不同[不同offer]，如等待多久）
        ，而SynchronousQueue目前可以理解为new LinkedBlockingQueue(1)，且用的api是无限期等待的api；
        ArrayBlockingQueue则是基于数据的且必须给定容量的queue；

        而对于线程池的策略是，如果有新的task进来，那么看是否达到了corePoolSize，没有则创建新的coreWorker（即线程）并将task立刻交由此worker执行；
        如果已经达到了corePoolSize的数量，那么会将task放入queue里缓存（注意是整个线程池共用一个queue，而非每个Worker/线程都有一个queue）；
        如果queue已经满了，那么会判断是否达到maximumPoolSize，如果达到了则说明此线程池彻底饱和，然后调用RejectedExecutionHandler；
        如果还未达到maximumPoolSize，那么会创建一个新的 非coreWorker，然后将此task直接给新的worker来执行；
        当 非coreWorker处理完毕后，且此时queue长时间没有新的task，那么达到idle时间后此 非coreWorker会被销毁；

        这里很重要的一点是，必须是达到corePoolSize，且queue（整个线程池共有）无法承载时，才创建新的非 coreWorker；

        注意，corePoolSize为 1，queue的capacity是3，那么其实可以存储4个task，因为coreWorker能够立刻消费一个；
        注意，加上corePoolSize是 1，而queue的capacity是3，那么一次性offer5个task，那么不是说一个coreWorker消费4个task，另一个消费1个，而是
        每个worker在处理当前task完毕后就会立刻去queue里获取新的task，因此这两个worker是分摊这些task的；
         */
        ExecutorService threadPool = new ThreadPoolExecutor(1, 3, 3,
                                                            TimeUnit.SECONDS, new LinkedBlockingQueue<>(3),
                                                            new DaemonThreadFactory("tester-pool"),
                                                            new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 5; i++) {
            threadPool.execute(() -> {
                System.err.println(Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(3L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

class DaemonThreadFactory implements ThreadFactory {

    private AtomicInteger threadNo = new AtomicInteger(1);
    private final String threadNameFormatStr;

    DaemonThreadFactory(String poolName) {
        this.threadNameFormatStr = MessageFormat.format("[{0}-%s]", poolName);
    }

    @Override
    public Thread newThread(Runnable r) {
        String threadName = String.format(this.threadNameFormatStr, String.valueOf(this.threadNo.getAndIncrement()));
        Thread newThread = new Thread(r, threadName);
        newThread.setDaemon(false);
        if (newThread.getPriority() != Thread.NORM_PRIORITY) {
            newThread.setPriority(Thread.NORM_PRIORITY);
        }
        return newThread;
    }
}