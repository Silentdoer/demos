package me.silentdoer.demoactivemqusage.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * 一般而言生产者和消费者【发布者和订阅者是在不同的服务/微服务里，否则消息队列的意义就没有了，直接就在推单接口里用线程池来执行业务逻辑即可】
 * 它的业务流程是，M系统一次性大量推单给N系统，N系统将推单的内容整合后形成相关Model对象，将这个对象作为消息offer到消息队列，
 * 然后S系统（也可能多个小系统）里的这个消息队列（Destination）的监听器（如果是多个小系统则只会有一个小系统的一个监听器收到某消息）收到
 * 消息后将消息放入线程池里去处理，当执行完毕后可以将结果写入数据库，然后U系统的跑批程序定期去获取数据并调用M系统的回调接口通知其推的单的状态；
 *
 * 这里的M系统比如是新网、N系统比如是AMP，S系统比如是AMP-Java，U系统比如也是可以AMP-Java的（或IMP）；
 * 这里是测试用法为了省事所以写一起了；
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/12/2018 6:10 PM
 */
@Configuration
public class ActiveMQConfig {

    @Value("${custom.activemq.order-queue}")
    private String orderQueue;

    @Value("${custom.activemq.background-order-queue}")
    private String bgOrderQueue;

    @Value("${custom.activemq.foo-topic}")
    private String fooTopic;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    //region 三个队列
    @Bean
    public Queue orderQueue() {
        return new ActiveMQQueue(this.orderQueue);
    }

    @Bean
    public Queue bgOrderQueue() {
        return new ActiveMQQueue(this.bgOrderQueue);
    }

    @Bean
    public Topic fooTopic() {
        return new ActiveMQTopic(this.fooTopic);
    }
    //endregion

    //region connection配置
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(this.username, this.password, this.brokerUrl);
    }

    /**
     * 类似连接池的概念
     * @return
     */
    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(connectionFactory());
        factory.setSessionCacheSize(10);
        return factory;
    }
    //endregion

    //region Jms工具类
    /**
     * 查了下似乎JmsTemplate和JmsMessagingTemplate都可以用，没查到具体区别是什么
     * @return
     */
    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(cachingConnectionFactory());
    }

    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate() {
        return new JmsMessagingTemplate(cachingConnectionFactory());
    }
    //endregion

    /**
     * 有了监听的目标和方法后，监听器还得和 MQ 关联起来，这样才能运作起来。这里的监听器可能不止一个，
     * 如果每个都要和 MQ 建立连接，肯定不太合适。所以需要一个监听容器工厂的概念，即接口JmsListenerContainerFactory，
     * 它会引用上面创建好的与 MQ 的连接工厂ActiveMQConnectionFactory，由它来负责接收消息以及将消息分发给指定的监听器。
     * 当然也包括事务管理、资源获取与释放和异常转换等。
     *
     * `@Bean`的name无法用${..}来注进去
     *
     * 下面这两个在生产者服务里是可以不用的【当然不同业务在一个服务里那么这个服务是可以即是生产者也是消费者】
     * @return
     */
    @Bean(value = "jmsListenerContainerQueue")
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue() {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(cachingConnectionFactory());
        // 设置并发数
        bean.setConcurrency("3-10");
        // 重连间隔时间
        bean.setRecoveryInterval(1000L);
        return bean;
    }

    @Bean(name = "jmsListenerContainerTopic")
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic() {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        //设置为发布订阅方式（topic）, 默认情况下使用的生产者消费者方式
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(cachingConnectionFactory());
        bean.setConcurrency("1");
        return bean;
    }
}
