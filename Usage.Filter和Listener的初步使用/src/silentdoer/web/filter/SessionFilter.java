package silentdoer.web.filter;

import silentdoer.web.servlet.MyServlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Enumeration;

public class SessionFilter implements Filter, FilterConfig, Serializable {
    private transient FilterConfig config;

    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    public void destroy() {
        this.config = null;
    }

    // 在web.xml里配置了多少Filter，在FilterChain里就有多少个Filter，而chain.doFilter(..)实际上是chain调用当前位置的Filter执行doFilter方法
    // class org.apache.catalina.core.ApplicationFilterChain，FilterChain由服务器中间件初始化，并且调用chain的doFilter方法
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("sessionFilter#doFilter()#" + (((HttpServletResponse)response).getHeader("test") == null));
        System.out.println("chain class:" + chain.getClass());
        HttpServletRequest hrequest = (HttpServletRequest)request;  // 请求时由服务器中间件先将请求给  第一个Filter处理
        HttpServletResponse hresponse = (HttpServletResponse)response;  // 响应时在chain.doFilter(..)后处理（拦截）
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response);

        try {
            Field f = chain.getClass().getDeclaredField("servlet");
            // 不这么做则对于private等访问权限的属性或方法将访问失败。
            f.setAccessible(true);
            // f.get(owner)类似m.invoke(owner,...)
            System.out.println(f.get(chain).getClass());
            // 通过set方法将chain的f属性设置为新的值
            //f.set(chain, new MyServlet());
        }catch (Exception ex){
            ex.printStackTrace();
        }

        /*try {
            // TODO 如果要令chain跳过n个Filter则可以通过反射将chain的pos属性+n即可
            Field ff = chain.getClass().getDeclaredField("pos");
            ff.setAccessible(true);
            ff.set(chain, ((int)ff.get(chain) + 1));  // 跳过TestFilter2
        }catch (Exception ex){
            ex.printStackTrace();
        }*/

        final String disableAllFilter = this.config.getInitParameter("disableAllFilter");
        // TODO 不chain.doFilter那么相当于阻挡客户端的请求
        if(disableAllFilter != null && disableAllFilter.toLowerCase().equals("true")){
            response.setCharacterEncoding("UTF-8");
            Writer writer = response.getWriter();
            writer.write("这是由Filter处理后的响应数据，没有经过servlet。");
            writer.close();
            return;
        }

        final String enableFilter = this.config.getInitParameter("enableFilter");
        if (enableFilter == null || enableFilter.toLowerCase().equals("false")) {
            // 内部是通过游标遍历每个filter（从下标0开始），如果最后发现没有filter了就调用chain中的servlet属性的service方法处理
            //，故要么所有chain中添加的Filter都会执行才到servlet，否则是到不到servlet这一步的。
            chain.doFilter(request, response);
            // TODO 注意这句话是service执行后才输出的，因为chain.doFilter(..)后发现没有Filter了于是调用servlet#service(..)方法处理
            //，这个过程是顺延下来的（即同步），故是先service后才到这里然后return
            System.out.println("sessionFilter#return");
            System.out.println("response的处理实际上是doFilter之后的代码处理：" + (((HttpServletResponse)response).getHeader("test") != null));
            // TODO 此时response是经过service处理后的response，如果要处理response则可以在return前处理。
            hresponse.setHeader("catalog", "uuuu");
            return;
        }

        String loginStrings = config.getInitParameter("loginStrings");  // 登录页面
        if(loginStrings == null){
            chain.doFilter(request, response);
            return;
        }
        String includeStrings = config.getInitParameter("includeStrings");  // 过滤资源后缀参数
        if(includeStrings == null){
            chain.doFilter(request, response);
            return;
        }
        String redirectPath = hrequest.getContextPath() + config.getInitParameter("redirectPath");  // 没有登陆转向页面
        String[] loginList = loginStrings.split(";");
        String[] includeList = includeStrings.split(";");
        if (!this.isContains(hrequest.getRequestURI(), includeList)) {// 只对指定过滤参数后缀进行过滤
            chain.doFilter(request, response);
            return;
        }

        if (this.isContains(hrequest.getRequestURI(), loginList)) {// 对登录页面不进行过滤
            chain.doFilter(request, response);
            return;
        }

        // TODO 这里才是真正的过滤代码
        String user = ( String ) hrequest.getSession().getAttribute("useronly");//判断用户是否登录
        if (user == null) {
            wrapper.sendRedirect(redirectPath);
            // TODO 由此可知一个Filter是可以主动控制是否继续由下一个Filter来过滤的，即请求不一定要经过所有Filter拦截并达到Servlet
            return;
        }else {
            // TODO 只有最后一个Filter也执行到了并仍然调用了chain.doFilter，这个请求才会被转入Servlet
            chain.doFilter(request, response);
            return;
        }
    }

    public static boolean isContains(String container, String[] regx) {
        boolean result = false;
        for (int i = 0; i < regx.length; i++) {
            if (container.indexOf(regx[i]) != -1) {
                return true;
            }
        }
        return result;
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
