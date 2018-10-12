import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/18/2018 3:54 PM
 */
public class GsonEntrance {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Date now = new Date();
        // 经过测试 gson不会特意 去忽略类似 Date这样的java 常见类型，所以很多时候还是得自己做判断
        System.out.println(gson.toJson(now));
        System.out.println(now);

        System.out.println(fetchUniqueMethodSymbol(Object.class.getMethods()[1]));
    }

    private static String fetchUniqueMethodSymbol(Method method) {
        return String.format("%s#%s%s",
                             method.getDeclaringClass().getName(),
                             method.getName(), Arrays.stream(method.getParameterTypes()).map(Class::getName).collect(Collectors.toList()));
    }
}
