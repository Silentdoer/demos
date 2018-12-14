import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootfilter.api.ApiResult;

import java.lang.reflect.Method;

/**
 * @author silentdoer
 * @version 1.0
 */
@Slf4j
public class Entrance {
    public static void main(String[] args) throws NoSuchMethodException {
        ApiResult result = new ApiResult();
        result.setCode(1).setMsg("success").setData("Hello");
        log.info(result.toString());
        Method method = result.getClass().getMethod("equals", Object.class);
        // 输出的是：me.silentdoer.springbootfilter.api.ApiResult，由此可见@Data确实是重写了equals、toString、hashCode方法
        log.info(method.getDeclaringClass().getName());
    }
}
