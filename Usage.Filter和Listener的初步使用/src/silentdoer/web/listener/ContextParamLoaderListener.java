package silentdoer.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextParamLoaderListener implements ServletContextListener {
    // 只在发布webapp时执行此方法一次
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println(servletContextEvent.getServletContext().getInitParameter("conPro"));
    }

    // 只在卸载webapp时执行此方法一次
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println(servletContextEvent.getServletContext().getInitParameter("conPro"));
    }
}
