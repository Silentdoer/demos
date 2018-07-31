import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 注意临界区的概念
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/19/2018 10:28 AM
 */
public class SemaphoreTest extends Thread {

    /**
     * 同时允许2个线程进入临界区
     */
    private static Semaphore semaphore = new Semaphore(2);

    @Override
    public void run() {
        try {
            // FLAG 注意一般而言是一个线程一次性获得一个permit，但是也可以单个线程一次性就获得多个permit，对于semaphore.acquire()就等同于下面
            semaphore.acquire(1);
            // 用来替换JDK自带的Date，它可以和Date无缝转换，因此属性可以用Date，然后操作的时候将其转换为Date对象，java里毫秒是SSS，C#貌似是fff
            DateTime dt = new DateTime(/*new Date()*/);
            // dt.toDate()
            System.out.println("sdfsssssss" + Thread.currentThread().getName() + "#"
                                       + DateTime.now().toString("yyyy-MM-dd HH:mm:ss:SSS", Locale.SIMPLIFIED_CHINESE));
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            semaphore.release(1);
        }
    }
}
