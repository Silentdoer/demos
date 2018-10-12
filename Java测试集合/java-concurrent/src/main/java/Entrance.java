import java.util.concurrent.Semaphore;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/19/2018 10:25 AM
 */
public class Entrance {

    public static void main(String[] args) {
        SemaphoreTest thd = new SemaphoreTest();
        SemaphoreTest thd2 = new SemaphoreTest();
        SemaphoreTest thd3 = new SemaphoreTest();
        thd.start();
        thd2.start();
        thd3.start();
    }
}
