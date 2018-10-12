package me.silentdoer.demosimpleproj.core.support;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/30/2018 5:46 PM
 */
public interface IAppAuthHandler extends Ordered {

    boolean supports(HandlerMethod method);

    /**
     * code的值就可以用于充当boolean判断了，因此这里data为null即可
     */
    ApiResult<Object> auth(HttpServletRequest request);
}
