package me.silentdoer.demosimpleproj.core.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import javax.activation.MimeType;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/30/2018 6:10 PM
 */
public class InputStreamReuseFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new IllegalArgumentException("不支持的参数");
        }
        // 对于Multipart的Content-Type不设置为可重复读取流，鉴权时也不对其进行验签，如果contentType是null，则默认其为application/json;charset=utf8
        String contentType = request.getContentType();
        if (StringUtils.isBlank(contentType)) {
            ReuseInputStreamServletRequest requestWrapper = new ReuseInputStreamServletRequest((HttpServletRequest) request);
            chain.doFilter(requestWrapper, response);
        } else if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(MediaType.valueOf(contentType))) {
            chain.doFilter(request, response);
        } else {
            ReuseInputStreamServletRequest requestWrapper = new ReuseInputStreamServletRequest((HttpServletRequest) request);
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {
    }
}