package silentdoer.web.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class MyServlet implements Servlet, ServletConfig, Serializable {
    private transient ServletConfig config;
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_TRACE = "TRACE";
    private static final String HEADER_IFMODSINCE = "If-Modified-Since";
    private static final String HEADER_LASTMOD = "Last-Modified";
    private static final String LSTRING_FILE = "javax.servlet.http.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");

    @Override
    public final void init(ServletConfig servletConfig) throws ServletException {
        this.config = servletConfig;
        this.init();
    }

    protected void init() throws ServletException {
        // TODO
    }

    @Override
    public void destroy() {
        this.config = null;
    }

    @Override
    public final void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request;
        HttpServletResponse response;
        try {
            request = (HttpServletRequest)servletRequest;
            response = (HttpServletResponse)servletResponse;
        } catch (ClassCastException ex) {
            throw new ServletException("non-HTTP request or response");
        }
        this.service(request, response);
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String method = request.getMethod();
        long lastModified;
        if (method.equals("GET")) {
            lastModified = this.getLastModified(request);
            if (lastModified == -1L) {
                this.doGet(request, response);
            } else {
                long ifModifiedSince;
                try {
                    ifModifiedSince = request.getDateHeader("If-Modified-Since");
                } catch (IllegalArgumentException var9) {
                    ifModifiedSince = -1L;
                }

                if (ifModifiedSince < lastModified / 1000L * 1000L) {
                    this.maybeSetLastModified(response, lastModified);
                    this.doGet(request, response);
                } else {
                    response.setStatus(304);
                }
            }
        } else if (method.equals("HEAD")) {
            /*lastModified = this.getLastModified(request);
            this.maybeSetLastModified(response, lastModified);
            this.doHead(request, response);*/
            throw new UnsupportedOperationException("暂不支持Method为HEAD的请求");
        } else if (method.equals("POST")) {
            this.doPost(request, response);
        } else if (method.equals("PUT")) {
            this.doPut(request, response);
        } else if (method.equals("DELETE")) {
            this.doDelete(request, response);
        } else if (method.equals("OPTIONS")) {
            this.doOptions(request, response);
        } else if (method.equals("TRACE")) {
            this.doTrace(request, response);
        } else {
            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[]{method};
            errMsg = MessageFormat.format(errMsg, errArgs);
            response.sendError(501, errMsg);
        }
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.config;
    }

    @Override
    public String getServletInfo() {
        return "This is MyServlet";
    }

    @Override
    public String getServletName() {
        return this.config.getServletName();
    }

    @Override
    public ServletContext getServletContext() {
        return this.getServletConfig().getServletContext();
    }

    @Override
    public String getInitParameter(String name) {
        return this.getServletConfig().getInitParameter(name);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return this.getServletConfig().getInitParameterNames();
    }

    //region
    public void log(String msg) {
        this.getServletContext().log(this.getServletName() + ": " + msg);
    }

    public void log(String message, Throwable t) {
        this.getServletContext().log(this.getServletName() + ": " + message, t);
    }
    //endregion

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_get_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }*/
        System.out.println("MyServlet#doGet()" + this.getServletContext().getAttribute("Count") + "##" + this.getServletContext().getInitParameter("conPro"));
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("test", "yes");
        Writer writer = resp.getWriter();
        writer.write(String.format("Hello, your address is:%s", req.getRemoteAddr()));
        writer.flush();
        writer.close();
    }

    protected long getLastModified(HttpServletRequest req) {
        return -1L;
    }

    /*protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (DispatcherType.INCLUDE.equals(req.getDispatcherType())) {
            this.doGet(req, resp);
        } else {
            NoBodyResponse response = new NoBodyResponse(resp);
            this.doGet(req, response);
            response.setContentLength();
        }
    }*/

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_post_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_put_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }

    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_delete_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }
    }

    private static Method[] getAllDeclaredMethods(Class<?> c) {
        if (c.equals(HttpServlet.class)) {
            return null;
        } else {
            Method[] parentMethods = getAllDeclaredMethods(c.getSuperclass());
            Method[] thisMethods = c.getDeclaredMethods();
            if (parentMethods != null && parentMethods.length > 0) {
                Method[] allMethods = new Method[parentMethods.length + thisMethods.length];
                System.arraycopy(parentMethods, 0, allMethods, 0, parentMethods.length);
                System.arraycopy(thisMethods, 0, allMethods, parentMethods.length, thisMethods.length);
                thisMethods = allMethods;
            }
            return thisMethods;
        }
    }

    // Method是OPTIONS则最终通过response在Header里添加Key是Allow，value类似"GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE"
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Method[] methods = getAllDeclaredMethods(this.getClass());
        boolean ALLOW_GET = false;
        boolean ALLOW_HEAD = false;
        boolean ALLOW_POST = false;
        boolean ALLOW_PUT = false;
        boolean ALLOW_DELETE = false;
        boolean ALLOW_TRACE = true;
        boolean ALLOW_OPTIONS = true;

        for(int i = 0; i < methods.length; ++i) {
            Method m = methods[i];
            if (m.getName().equals("doGet")) {
                ALLOW_GET = true;
                ALLOW_HEAD = true;
            }

            if (m.getName().equals("doPost")) {
                ALLOW_POST = true;
            }

            if (m.getName().equals("doPut")) {
                ALLOW_PUT = true;
            }

            if (m.getName().equals("doDelete")) {
                ALLOW_DELETE = true;
            }
        }

        String allow = null;
        if (ALLOW_GET) {
            allow = "GET";
        }

        if (ALLOW_HEAD) {
            if (allow == null) {
                allow = "HEAD";
            } else {
                allow = allow + ", HEAD";
            }
        }

        if (ALLOW_POST) {
            if (allow == null) {
                allow = "POST";
            } else {
                allow = allow + ", POST";
            }
        }

        if (ALLOW_PUT) {
            if (allow == null) {
                allow = "PUT";
            } else {
                allow = allow + ", PUT";
            }
        }

        if (ALLOW_DELETE) {
            if (allow == null) {
                allow = "DELETE";
            } else {
                allow = allow + ", DELETE";
            }
        }

        if (ALLOW_TRACE) {
            if (allow == null) {
                allow = "TRACE";
            } else {
                allow = allow + ", TRACE";
            }
        }

        if (ALLOW_OPTIONS) {
            if (allow == null) {
                allow = "OPTIONS";
            } else {
                allow = allow + ", OPTIONS";
            }
        }

        resp.setHeader("Allow", allow);
    }

    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String CRLF = "\r\n";
        StringBuilder buffer = (new StringBuilder("TRACE ")).append(req.getRequestURI()).append(" ").append(req.getProtocol());
        Enumeration reqHeaderEnum = req.getHeaderNames();

        while(reqHeaderEnum.hasMoreElements()) {
            String headerName = (String)reqHeaderEnum.nextElement();
            buffer.append(CRLF).append(headerName).append(": ").append(req.getHeader(headerName));
        }

        buffer.append(CRLF);
        int responseLength = buffer.length();
        resp.setContentType("message/http");
        resp.setContentLength(responseLength);
        ServletOutputStream out = resp.getOutputStream();
        out.print(buffer.toString());
        out.close();
    }

    private void maybeSetLastModified(HttpServletResponse resp, long lastModified) {
        if (!resp.containsHeader("Last-Modified")) {
            if (lastModified >= 0L) {
                resp.setDateHeader("Last-Modified", lastModified);
            }
        }
    }
}
