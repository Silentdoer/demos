import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 11/1/2018 2:48 PM
 */
public class FieldSetTest {

    public static void main(String[] args) {
        DateTime dt1 = new DateTime();
        System.out.println(dt1);
        // TODO 设置具体的年份
        System.out.println(dt1.withYear(2000));
        // TODO 设置当前世纪（2000世纪）的年份，所以这里是2003
        System.out.println(dt1.withYearOfCentury(3));
        // 报错
        //System.out.println(dt1.withYearOfCentury(2015));
        LocalDate ld1 = new LocalDate();
        System.out.println(ld1);
        System.out.println(ld1.withYear(2030));
        LocalDate ld2 = ld1.withYear(2000).withMonthOfYear(2).withDayOfMonth(29);
        System.out.println(ld2);
        // TODO 注意这里产生了很神奇的地方，由于1998年是平年，所以原先的2.29自动转换成了2.28号
        System.out.println(ld2.withYear(1998));
    }
}
