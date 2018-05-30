package me.silentdoer.springbootservletinputstreamtest.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@WebFilter(urlPatterns = "/**")
@Slf4j
@Service
public class DefaultFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("length:{}", servletRequest.getContentLength());
        ServletInputStream inputStream = servletRequest.getInputStream();
        log.info("available:{}", inputStream.available());
        byte[] bytes = new byte[2048];
        int count = inputStream.read(bytes);
        log.info("count:{}", count);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
