package me.silentdoer.demosimpleproj.core.component;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demosimpleproj.core.support.ApiResult;
import me.silentdoer.demosimpleproj.core.support.ApiResultEnum;
import me.silentdoer.demosimpleproj.core.support.IAppAuthHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 责任链模式的最通用的写法是每一个Filter都有一个next，然后构成一个非环形链表，
 * 由第一个Filter来doFilter（这些Filter是可以有优先级的），然后每个Filter过滤后
 * 可以决定是否需要进一步由next来doFilter，因此这种写法可以用一个Chain对象来封装从而用for的形式实现
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 4:09 PM
 */
public class AppAuthHandlerChain implements ApplicationContextAware {

    /**
     * 配置的所有的IAppAuthHandler，类似配置的所有的RequestMappingHandlerMapping
     */
    private List<IAppAuthHandler> appAuthHandlers = new LinkedList<>();

    private HttpServletRequest request;

    public ApiResult<Object> doAuth(HandlerMethod method) {
        ApiResult<Object> result = ApiResultEnum.ok();
        // 这里当Mapping方法上没有任何的Auth注解，那么即为任意态都可以访问
        for (IAppAuthHandler appAuthHandler : appAuthHandlers) {
            if (appAuthHandler.supports(method)) {
                result = appAuthHandler.auth(request);
                // 如果某一层的Handler auth失败，则不再继续后续的auth
                if (!ApiResultEnum.NORMAL.correspond(result)) {
                    return result;
                }
            }
        }
        return result;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 这一步本身就是会将未生成bean的BeanDefinition对象先一步生成，所以不需要用ApplicationListener来实现
        Map<String, IAppAuthHandler> beansOfType = applicationContext.getBeansOfType(IAppAuthHandler.class);
        appAuthHandlers.addAll(beansOfType.values());
        appAuthHandlers.sort(Comparator.comparingInt(Ordered::getOrder));
    }
}
