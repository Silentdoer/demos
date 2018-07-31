package me.silentdoer.springbootsessionidgeneratestrategy.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Header和Cookie通用，优先Cookie
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/20/2018 10:31 AM
 */
@Slf4j
public class MyHttpSessionIdResolver implements HttpSessionIdResolver {

    /**
     * signName的值就类似JSESSIONID（不过它是通用于header里）
     */
    private final String signName;

    public MyHttpSessionIdResolver(String signName) {
        this.signName = signName;
    }

    public MyHttpSessionIdResolver() {
        this("SESSION");
    }

    /**
     * 这个是当需要request.getSession()时会通过这个方法来获取request里的SESSION然后和sessionRepository里进行对比看是否已经存在
     *
     * @param request
     * @return
     */
    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        // TODO 重要，如果客户端没有传cookie，那么这里是null而非空数组（但是对于List似乎是即便没有任何元素也是空List而非null？）
        log.info("request cookies is null?:{}", Objects.isNull(request.getCookies()));
        if (Objects.nonNull(request.getCookies())) {
            List<String> collect = Arrays.stream(request.getCookies()).filter(a -> Objects.equals(a.getName(), this.signName))
                    .map(Cookie::getValue).collect(Collectors.toList());
            if (Objects.nonNull(collect) && !collect.isEmpty()) {
                log.info("{}", collect.toString());
                return collect;
            }
        }
        String header = request.getHeader(this.signName);
        return Collections.singletonList(header);
    }

    /**
     * 这个是当request.getSession()后得到session对象的Id在return response会执行的操作（就类似Set-Cookie）
     *
     * @param request
     * @param response
     * @param sessionId
     */
    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        response.setHeader(this.signName, sessionId);
        response.addCookie(new Cookie(this.signName, sessionId));
    }

    /**
     * 令signName立刻过期
     * @param request
     * @param response
     */
    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(this.signName, "");
        response.addCookie(new Cookie(this.signName, ""));
    }
}
