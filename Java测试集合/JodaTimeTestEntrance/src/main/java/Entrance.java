import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/24/2018 3:04 PM
 */
public class Entrance {

    public static void main(String[] args) {
        Calendar instance = Calendar.getInstance();
        instance.set(2018, 1, 1);
        //instance.add(Calendar.DAY_OF_YEAR, -6);
        System.out.println(instance.getTime());
        DateTime dt = new DateTime(instance);
        System.out.println(dt.plusDays(-6));
        instance.set(2017, Calendar.DECEMBER, 30);
        DateTime dt2 = new DateTime(instance);
        System.out.println(dt2);
        dt2 = dt2.plusDays(10);
        System.out.println(dt2 + "#######");

        DateTime dt3 = new DateTime();
        System.out.println(dt3);
        System.out.println(dt3.plusDays(40));

        // TODO 经过测试如果要对日期进行计算，是要用Calendar，Date做不到（天的加减无法造成月和年的加减）
        // TODO
        instance.set(2017, Calendar.DECEMBER, 30);
        System.out.println("----------------------");
        System.out.println(instance.getTime());
        instance.add(Calendar.DATE, 1);
        System.out.println(instance.getTime());
        instance.add(Calendar.DATE, 2);
        System.out.println(instance.getTime());
        LocalDateTime of = LocalDateTime.of(2017, 12, 29, 0, 0, 0);
        System.err.println(of);
        System.err.println(of.plusDays(10));
        System.err.println(of);
        System.err.println(LocalDateTime.now());
        System.out.println();



    }
}
