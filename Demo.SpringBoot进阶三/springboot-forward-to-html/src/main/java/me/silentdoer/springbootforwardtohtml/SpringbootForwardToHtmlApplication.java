package me.silentdoer.springbootforwardtohtml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO 这里要实现类似Tomcat自动拦截直接对WEB-INF目录的访问，而自己这里是拦截所有http://localhost/private/..；这里用拦截器实现，凡是直接访问http://localhost/private/开头的都返回404
 * TODO 或者用过滤器实现（还是用过滤器吧，感觉更底层一点）
 */
@SpringBootApplication
public class SpringbootForwardToHtmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootForwardToHtmlApplication.class, args);
    }
}
