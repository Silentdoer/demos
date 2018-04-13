package me.silentdoer.redisrabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/11/18 10:23 PM
 */
public class MqProducer {
    private static ConnectionFactory factory;

    public static void main(String[] args) throws IOException, TimeoutException {
        initFactory();
        Connection connection = factory.newConnection();
        // channel的实现方式其实就是注册机制，即我通过某个tcp协议的socket对象，调有createChannel其实就是发送如：create channel 0的数据
        // 然后服务端收到后在相关注册表里添加条目 0 - conn0 - user - vhost 之类的数据，然后返回true给客户端，然后客户端也添加本地注册表
        // 这样就通过每次发送数据都加一个channel从而将不同channel的数据分隔开
        Channel chann0 = connection.createChannel(1);  // TODO 此值要大于0
        Scanner scanner = new Scanner(System.in);
        String msg = "";
        while(!"exit".equals(msg)){
            msg = scanner.nextLine();
            chann0.basicPublish("", "test", null, msg.getBytes("utf8"));
        }
        // ""表示是默认的exchange，但是还是得显示指定，TODO 注意不能是null
        //第三个参数props是消息包含的属性信息。RabbitMQ的消息属性和消息体是分开的，不像JMS消息那样同时包含在javax.jms.Message对象中，这一点需要特别注意
        //chann0.basicPublish("", "test", null, "payload的消息".getBytes("utf8"));
        //chann0.queueDeclare("name", true, false, false, null);  // 创建一个queue（默认会创建一个default-queuename-queue的binding）
        //chann0.exchangeDeclare(..);  // 创建一个exchange，只能指定builtin的类型，即direct/fanout/header/topic。。
        //chann0.exchangeBind(..);  // 设置一个binding，将exchange和exchange绑定，类似责任链处理
        //chann0.queueBind(..);  // 设置一个binding，将queue和exchange绑定
        chann0.close();  // 注意，其实只是在本地和服务器的注册表里删除了此channel的信息，以及将此channel设置为不可用而以
        connection.close();  // 真正关闭tcp连接
    }

    public static void initFactory(){
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setConnectionTimeout(10000);
        //factory.setShutdownTimeout();
    }
}
