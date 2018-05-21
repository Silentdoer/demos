package me.silentdoer.springbootforwarttohtml.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Slf4j
@Service
@WebFilter(urlPatterns = "/*")
public class DefaultWebFilter implements Filter {
    private static String MOCK_WEB_INF = "/private";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 不需要保存这个值
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("DefaultWebFilter in");
        if(!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)){
            throw new IllegalStateException("不是符合要求的Http请求");
        }
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        log.info("servletPath is {}", servletPath);
        if(servletPath.startsWith(DefaultWebFilter.MOCK_WEB_INF)){
            response.sendError(404, "资源未找到");
            return;
        }
        log.info("continue doFilter");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
