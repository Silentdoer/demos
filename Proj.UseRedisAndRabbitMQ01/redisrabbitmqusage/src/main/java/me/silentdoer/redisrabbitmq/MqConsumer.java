package me.silentdoer.redisrabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/11/18 10:43 PM
 */
public class MqConsumer {
    private static ConnectionFactory factory;

    public static void main(String[] args) throws IOException, TimeoutException {
        initFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(3);  // channel 不需要和producer的一样
        // Consumer 是回调函数，即channel.basicConsume(..)获得一条消息后会调有此观察者的
        //handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)方法
        Consumer consumer = new DefaultConsumer(channel){
            // 默认此观察者的命令方法是什么都不做的
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("收到一条需要我方处理的消息:" + message);
            }
        };
        while (true){
            // 注意，是channel在订阅消息，这个订阅和监听器不太一样，需要不断的订阅（有点像C#里的BeginAccept，获取后需要继续BeginAccept才行）
            channel.basicConsume("test", true, consumer);
        }
    }

    public static void initFactory(){
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");  // TODO 注意，生产环境消费者和生产者的账户是不一样的，只不过vhost一样，ip:port也一样
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setConnectionTimeout(10000);
    }
}
