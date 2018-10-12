package me.silentdoer.demousagerabbitmq.rabbitmq.consumer;

import me.silentdoer.demousagerabbitmq.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/24/2018 11:29 AM
 */
@Component
@RabbitListener(queues = "test_queue_1")
public class MessageReceiver {

    /**
     * 不同的消息类型会转发到不同的Handler进行处理，如果没有匹配的类型则由isDefault=true的来处理
     * 不过似乎isDefault=true的参数最好是字节数组类型？？
     */
    @RabbitHandler(isDefault = true)
    public void receive(String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.err.println(String.format("消息接收时间:%s，收到的消息是:%s", sdf.format(new Date()), msg));
    }

    @RabbitHandler
    public void receive(User msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.err.println(String.format("##消息接收时间:%s，收到的消息是:%s", sdf.format(new Date()), msg.getName()));
    }
}
