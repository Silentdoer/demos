package me.silentdoer.demousagerabbitmq.service.impl;

import me.silentdoer.demousagerabbitmq.model.User;
import me.silentdoer.demousagerabbitmq.service.IMessageService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/24/2018 11:05 AM
 */
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMsg(String queueName, String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(String.format("消息发送时间:%s，发送内容:%s", sdf.format(new Date()), msg));
        // MessagePostProcessor和BeanPostProcessor一样就是在真正Post（发布、提交）Message之前对Message做一些处理
        // 指定由test_exchange交换机来将消息路由到具体的消息队列
        rabbitTemplate.convertAndSend("test_exchange", queueName, msg, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 延时3秒后才发布到真正的消息队列由消费者消费
                message.getMessageProperties().setHeader("x-delay", 3000);
                return message;
            }
        });
    }

    @Override
    public void sendMsg(String queueName, User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(String.format("消息发送时间:%s，发送内容:%s", sdf.format(new Date()), user.getName()));
        // MessagePostProcessor和BeanPostProcessor一样就是在真正Post（发布、提交）Message之前对Message做一些处理
        // 指定由test_exchange交换机来将消息路由到具体的消息队列
        rabbitTemplate.convertAndSend("test_exchange", queueName, user, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 延时3秒后才发布到真正的消息队列由消费者消费
                message.getMessageProperties().setHeader("x-delay", 3000);
                return message;
            }
        });
    }
}
