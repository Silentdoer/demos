package me.silentdoer.springbootwebsocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Configuration
public class Infrastructure {
    /**
     * ServerEndpointExporter是spring的依赖spring-boot-starter-websocket的pom，而且@Bean是单例
     * TODO 经过测试这个确实不能少
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
