package me.silentdoer.demosimpleproj.api.common.component;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import me.silentdoer.demosimpleproj.core.component.AppAuthHandlerChain;
import me.silentdoer.demosimpleproj.core.component.RequiresLoginAuthHandler;
import me.silentdoer.demosimpleproj.core.support.*;
import me.silentdoer.demosimpleproj.core.util.IOUtils;
import me.silentdoer.demosimpleproj.core.util.IpUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.annotation.AnnotationBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 系统鉴权拦截器，包括进出口的日志记录
 * 日志分为两部分来处理，Interceptor里只记录基本的数据，只有鉴权成功且参数成功赋值后在通过Aspect来进一步记录；
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/29/2018 7:10 PM
 */
public class AppAuthInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(AppAuthInterceptor.class.getSimpleName());

    @Autowired
    private AppAuthHandlerChain chain;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 这里也是用log.info就行，线上是不需要这些日志的，只需要报错的日志即可
        logger.info("【uuid:{}, remoteIp:{}, requestURL:{}, contentLength:{}, contentType:{}, queryStrLength:{}】",
                    Api.getThreadId(), IpUtils.getClientRealIp(request), request.getRequestURL(),
                    request.getContentLength(), request.getContentType(),
                    Objects.isNull(request.getQueryString()) ? "null" : request.getQueryString().length());

        // 虽然SpringMVC里HandlerAdapter有很多种类型，比如就是一个servlet对象、Name什么的，但本项目不用它们
        if (handler instanceof HandlerMethod) {
            // 必须提供客户端版本
            if (Objects.isNull(request.getHeader(Api.CLIENT_VERSION))) {
                // 将提示信息通知客户端
                IOUtils.write(ApiResultEnum.failTip("当前客户端版本过低，请升级后重试"), response.getOutputStream());
                return false;
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            this.chain.setRequest(request);
            ApiResult<Object> result = this.chain.doAuth(handlerMethod);
            if (ApiResultEnum.NORMAL.correspond(result)) {
                // 有权限访问，开始记录真正的业务逻辑耗时多少
                Api.getStopwatch().start();
                return true;
            } else {
                IOUtils.write(ApiResultEnum.failTip(result.getMsg()), response.getOutputStream());
                return false;
            }

        } else {
            return super.preHandle(request, response, handler);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}