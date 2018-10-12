import org.apache.commons.lang3.StringUtils;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/20/2018 6:07 PM
 */
public class StringUtilsEntrance {

    public static void main(String[] args) {
        String str = StringUtils.capitalize("a");
        String ff = StringUtils.capitalize("fFa");
        // A
        System.out.println(str);
        // FFa（因此其实不符合IDEA的生成setfFa()）
        System.out.println(ff);
    }
}
