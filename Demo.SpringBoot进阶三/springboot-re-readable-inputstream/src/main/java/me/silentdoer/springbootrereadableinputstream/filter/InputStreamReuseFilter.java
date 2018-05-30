package me.silentdoer.springbootrereadableinputstream.filter;

import me.silentdoer.springbootrereadableinputstream.support.ReuseInputStreamServletRequest;

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
        ReuseInputStreamServletRequest requestWrapper = new ReuseInputStreamServletRequest((HttpServletRequest) request);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {
    }
}
