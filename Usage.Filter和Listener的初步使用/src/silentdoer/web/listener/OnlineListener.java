package silentdoer.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineListener implements HttpSessionListener {
    private long onlineCount = 0;

    // 注意这个是必须有session创建才会调用，即有request.getSession()
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        onlineCount++;
        // session是针对context和客户端浏览器（更具体的是一个TCP/IP连接）而言的，而非context中的servlet
        httpSessionEvent.getSession().getServletContext().setAttribute("Count", onlineCount);
        httpSessionEvent.getSession().setAttribute("keySe", "valueSe");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        onlineCount--;
        httpSessionEvent.getSession().getServletContext().setAttribute("Count", onlineCount);
    }
}
