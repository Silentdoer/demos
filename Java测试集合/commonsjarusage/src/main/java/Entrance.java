import org.apache.commons.lang3.StringUtils;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/18/2018 3:03 PM
 */
public class Entrance {

    public static void main(String[] args) {
        String overlay = StringUtils.overlay("13136161100", "*****", 3, 8);
        System.out.println(overlay);
    }
}
