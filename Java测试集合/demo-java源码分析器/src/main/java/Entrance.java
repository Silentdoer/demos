import me.silentdoer.sourceanalysis.core.JavaSourceHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/10/2018 5:11 PM
 */
public class Entrance {

    public static void main(String[] args) {
        JavaSourceHelper helper = new JavaSourceHelper("E:\\Documents\\IDEAProjs\\Java测试集合\\demo-java源码分析器\\src\\main\\java\\me\\silentdoer\\sourceanalysis\\Test.java");
        System.out.println(helper.getClassName());
        System.out.println(helper.getAbsClassName());
        System.out.println(helper.getSuperClsName());
        System.out.println(helper.getAbsSuperClsName());
        System.out.println(helper.getAvailFields());
        System.out.println(helper.correspondClass("me.silentdoer.sourceanalysis.Test"));
    }
}
