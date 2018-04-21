import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Entrance {
    public static void main(String[] args){
        System.out.println(String.class.isAssignableFrom(Object.class));  // false
        System.out.println(Object.class.isAssignableFrom(String.class));  // true
        System.out.println(Serializable.class.isAssignableFrom(Object.class));  // false
        String str = "uuu.mmmm";
        Pattern pattern = Pattern.compile("[.]");  // 在[]内部.不再代表除了\r\n两个字符以外的所有字符
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.groupCount());
        System.out.println(pattern.split(str).length);
        System.out.println(str.split("\\.").length);
    }
}
