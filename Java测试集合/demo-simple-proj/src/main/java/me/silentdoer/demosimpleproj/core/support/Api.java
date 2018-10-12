package me.silentdoer.demosimpleproj.core.support;

import com.google.common.base.Stopwatch;
import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/29/2018 6:49 PM
 */
public class Api {

    /**
     * 当前会话的唯一ID，可用于调试
     */
    private static ThreadLocal<String> uuid = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());

    /**
     * 用于记录请求耗时
     */
    private static ThreadLocal<Stopwatch> stopwatch = ThreadLocal.withInitial(Stopwatch::createUnstarted);

    //region 系统参数，放在header中

    /**
     * 随机字符串
     */
    public static final String CLIENT_NONCE = "client_nonce";

    /**
     * 客户端调用时的时间戳
     */
    public static final String CLIENT_TIMESTAMP = "client_timestamp";

    /**
     * 服务端给客户端提供的AppKey
     */
    public static final String APP_KEY = "app_key";

    /**
     * 客户端给出的签名（如果是非登录态接口可以不提供）
     */
    public static final String APP_AUTHORIZATION = "Authorization";

    /**
     * 客户端的类型、WAP、Android、IOS、WEB等等
     */
    public static final String CLIENT_TYPE = "client_type";

    /**
     * 客服端识别的服务端的版本号
     */
    public static final String SERVER_VERSION = "server_version";

    /**
     * 客户端的版本号
     */
    public static final String CLIENT_VERSION = "client_version";

    /**
     * 是否是WIFI情况
     */
    public static final String CLIENT_IS_WIFI = "client_is_wifi";

    /**
     * 客户端的真实IP（起点IP而非经过代理后的IP）
     */
    public static final String CLIENT_IP = "client_ip";

    /**
     * 客户端的唯一ID，可用于可信环境监测（即用户是否在该处登录过）
     */
    public static final String CLIENT_ID = "client_id";
    //endregion

    private Api() {}

    public static String getThreadId() {
        return String.format("[%s]", uuid.get());
    }

    public static Stopwatch getStopwatch() {
        return stopwatch.get();
    }
}
