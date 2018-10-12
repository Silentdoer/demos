import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/12/2018 2:44 PM
 */
public class Entrance {

    public static void main(String[] args) {
        Stopwatch watch = Stopwatch.createStarted();
        /*for (int i = 0; i < 10000; i++) {
            String s = UUID.randomUUID().toString();
        }*/
        String tmp;
        for (int i = 0; i < 10000; i++) {
            String random = RandomStringUtils.random(32);
            tmp = random;
        }
        watch.stop();
        // 产生1W个UUID花费了100毫秒，即生成一个UUID大致花费100分之一毫秒，即产生100个UUID才花费一毫秒
        System.out.println(watch.elapsed().toMillis());
        // 而用RandomStringUtils.random(32)生成1W个则只花了60毫秒
    }
}
