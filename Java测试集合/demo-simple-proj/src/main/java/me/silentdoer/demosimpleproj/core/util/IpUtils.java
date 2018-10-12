package me.silentdoer.demosimpleproj.core.util;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demosimpleproj.core.support.Api;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/30/2018 5:09 PM
 */
public class IpUtils {

    private IpUtils() {}

    public static String getClientRealIp(HttpServletRequest request) {
        String clientIP = request.getHeader(Api.CLIENT_IP);
        return Objects.isNull(clientIP) ? request.getRemoteHost() : clientIP;
    }
}
