import java.util.concurrent.TimeUnit;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class Entrance extends Thread {
    public static void main(String[] args) throws InterruptedException {
        Entrance thd1 = new Entrance();
        // thd的id是在Thread构造方法里就会通过一个统一的generator生产的；
        thd1.setName("啦啦啦-" + thd1.getId());
        Entrance thd2 = new Entrance();
        thd2.setName("密麻麻-" + thd2.getId());
        thd1.start();
        TimeUnit.SECONDS.sleep(1);
        thd2.start();
    }

    @Override
    public void run() {
        final Class<?> clazz = Entrance.class;
        // TODO 经过测试确实是锁的对象内容，而不是对象的引用变量
        synchronized (clazz) {
            // Name里就自动会包括threadId，但是如果主动设置了thd的name那么是不会有id的，可以自己自定义一个生产唯一threadId的generator，然后由它分配id即可（不是说要和thd.Id一样）
            // 上面的适合表述某个线程池的第几个线程， 如果不是线程池可以setName时同时thd.getId()来设置
            System.out.println(Thread.currentThread().getName());
            try {
                // TODO 这里阻塞3秒使得thd2能够在thd1退出synchronized之前到达synchronized，如果
                // TODO synchronized只是锁对象引用那么显然thd2会直接输出，因为thd1和thd2锁住的虽然是同一个对象但是是不同的引用
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
