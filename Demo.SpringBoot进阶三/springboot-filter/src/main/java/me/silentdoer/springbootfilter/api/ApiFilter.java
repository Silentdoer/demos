package me.silentdoer.springbootfilter.api;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootfilter.api.user.req.LoginRequest;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 经过WebFilter注解后这个filter就能够配置到web.xml里
 * @author silentdoer
 * @version 1.0
 */
@Slf4j
@Service
@WebFilter(urlPatterns = "/**")
public class ApiFilter implements Filter {
    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(1).setMsg("success").setData("UUUUU");
        System.out.println(apiResult);
        log.info("执行到了ApiFilter的doFilter方法里");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        config = null;
    }
}
