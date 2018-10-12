import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.reflect.TypeUtils;

import java.util.Date;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/18/2018 11:54 AM
 */
public class GsonEntrance {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Date now = new Date();
        // 经过测试 gson不会特意 去忽略类似 Date这样的java 常见类型，所以很多时候还是得自己做判断
        System.out.println(gson.toJson(now));
        System.out.println(now);
    }
}
