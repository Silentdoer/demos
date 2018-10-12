package me.silentdoer.demousagerabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/24/2018 10:40 AM
 */
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMqConfig {

    private String host;

    private int port;

    private String username;

    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host, port);
        // 一般而言是建立生产者和消费者用的账户是不一样的，因此这里其实可以用另外一个，而MessageReceiver里用的则是application.properties里的
        cachingConnectionFactory.setUsername(this.username);
        cachingConnectionFactory.setPassword(this.password);
        cachingConnectionFactory.setVirtualHost("/");
        cachingConnectionFactory.setPublisherConfirms(true);
        return cachingConnectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    // region Rabbit Exchange和Queue配置
    /**
     * delayExchange是延时发布消息的交换机，即客户端在A时间戳publish消息，这个交换机会延时指定时间后才将消息路由发布到对应的消息队列
     * TODO 注意这里可以这么用是因为安装了：rabbitmq_delayed_message_exchange插件
     */
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>(4);
        args.put("x-delayed-type", ExchangeTypes.DIRECT);
        // 第一个是交换机的名字，客户端给RabbitMQ系统（一个入口）发消息时由于需要由交换机进行路由，所以要指定是哪个交换机
        return new CustomExchange("test_exchange", "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue queue() {
        return new Queue("test_queue_1", true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(delayExchange()).with("test_queue_1").noargs();
    }
    // endregion

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}