package me.silentdoer.springbootrereadableinputstream.filter;

import me.silentdoer.springbootrereadableinputstream.support.ReuseInputStreamServletRequest;
import org.springframework.util.Assert;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class InputStreamReuseFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new IllegalArgumentException("不支持的参数");
        }
        Assert.notNull(request, "request 不能为null");
        //ReuseInputStreamServletRequest requestWrapper = new ReuseInputStreamServletRequest((HttpServletRequest) request);
        // SpringBoot自带了相关实现类；
        //ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(((HttpServletRequest) request));
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
