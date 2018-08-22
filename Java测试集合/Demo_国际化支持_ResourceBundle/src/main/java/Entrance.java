import org.apache.commons.lang3.math.NumberUtils;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/15/2018 11:40 AM
 */
public class Entrance {

    /**
     * TODO 国际化支持是指key-value的 value 国际化，但是key是统一的（所以key只好用ASCII）
     */
    public static void main(String[] args) {
        // Locale.getDefault()获得的是当地的语言环境（包括语言、国家，如zh_CN、en_US）；
        // 由于当前语言环境是中国大陆，所以会从resources目录（classes目录）获得config_zh_CN.properties文件
        // 如果没有找到这个文件则用config.properties，如果也没找到则抛MissingResourceException
        // TODO 注意，如果是中国大陆环境，没有找到config_zh_CN.properties和config.properties但是有config_en_US.properties  也是报错
        // 这里也可以直接用ResourceBundle.getBundle(..)
        ResourceBundle config = PropertyResourceBundle.getBundle("config", Locale.getDefault());
        System.out.println(config == null);
        // 找不到key直接抛异常了而非返回null（不过可以理解，这个严格检查后是完全可以避免的问题）
        String value = config.getString("key");
        // TODO 一个key可以有多个value？？即配置多个相同key的pair？？
        //config.getStringArray()
        // 将value转换为对应的类型
        Double dVal;
        if (NumberUtils.isCreatable(value)) {
            dVal = NumberUtils.toDouble(value);
        } else {
            dVal = null;
        }
        System.out.println(dVal);
    }
}
