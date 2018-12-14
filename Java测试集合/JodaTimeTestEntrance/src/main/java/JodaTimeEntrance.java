import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/24/2018 8:06 PM
 */
public class JodaTimeEntrance {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        DateTime dt = new DateTime(calendar);
        System.out.println(dt);
        LocalDate dt3 = new LocalDate(calendar);
        System.out.println(dt3);
        calendar = Calendar.getInstance();
        calendar.set(2018, 7, 24);
        LocalDate dt2 = new LocalDate(calendar);
        System.out.println(dt2);
        System.out.println(dt2.equals(dt3));

        LocalDate dt4 = new LocalDate(new Date());

        LocalDate dt5 = new LocalDate(new Date());
        System.out.println(dt4.equals(dt5));
        System.out.println(dt5.toString("yyyy#MM$dd"));
        System.out.println(DateTimeFormatter.BASIC_ISO_DATE);
        Months.monthsBetween(dt5, dt4);
        // 相差多少天
        System.out.println(Days.daysBetween(dt2, dt4).getDays());

        // 格式化时间
        DateTime currentDateTime = new DateTime();
        System.out.println(currentDateTime.toString("yyyy-MM-dd HH:mm:ss"));

        // 指定时区格式化（没发现有什么不同。。）
        String format = "yyyy-MM-dd HH:mm:ss";
        DateTime dateTime = new DateTime();
        System.out.println(dateTime.toString(format, Locale.GERMAN));

        System.out.println(dt4);
        System.out.println(dt4.plusDays(50));
    }
}
