package me.silentdoer.springbootwebfilter2.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

/**
 * TODO 不要用@Component的形式，而要通过@Bean来添加，这样可以有一些额外的配置
 * @author liqi.wang
 * @version 1.0.0
 */
@WebFilter(urlPatterns = "/*")
@Slf4j
public class TestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletInputStream inputStream = request.getInputStream();
        log.info("access test Filter, request contentLength:{}, available:{}", request.getContentLength(), BooleanUtils.toBoolean(inputStream.available()));
        byte[] body = new byte[1024 * 4];
        int count = inputStream.read(body);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(String.valueOf(count).getBytes(StandardCharsets.UTF_8.name()));
        //chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
