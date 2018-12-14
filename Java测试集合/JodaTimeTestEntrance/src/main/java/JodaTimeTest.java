import org.joda.time.DateTime;

import java.util.Date;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/29/2018 4:35 PM
 */
public class JodaTimeTest {

    public static void main(String[] args) {
        DateTime now = new DateTime();
        System.out.println(now);
        Date dt = new Date();
        System.out.println(dt);
        Date tmp = now.toDate();
        System.out.println(tmp);
        System.out.println(dt.equals(tmp));
        System.out.println(dt.getTime());
        System.out.println(tmp.getTime());
    }
}
