package silentdoer.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;

public class TestFilter2 implements Filter, FilterConfig, Serializable {
    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("TestFilter2#doFilter(..) in");
        String enableFilter = this.config.getInitParameter("enableFilter");
        if(enableFilter == null || enableFilter.toLowerCase().equals("false")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // TODO doRequestFilter code
        System.out.println("TestFilter2#doFilter(..) request");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("TestFilter2#doFilter(..) response");
        // TODO doResponseFilter code
    }

    @Override
    public void destroy() {
        this.config = null;
    }

    @Override
    public String getFilterName() {
        return this.config.getFilterName();
    }

    @Override
    public ServletContext getServletContext() {
        return this.config.getServletContext();
    }

    @Override
    public String getInitParameter(String name) {
        return this.config.getInitParameter(name);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return this.config.getInitParameterNames();
    }
}
