package me.silentdoer.demoactivemqusage.api.activemq.service.impl;

import me.silentdoer.demoactivemqusage.api.activemq.service.ITopicListenerService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * 经过验证，Topic的队列的监听者在该队列产生消息后所有的监听者都会收到消息，如果没有监听者则消息会被丢弃【RabbitMQ里貌似是此消息会进入死信队列】
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/13/2018 11:43 AM
 */
@Service
public class Topic2ListenerServiceImpl implements ITopicListenerService<String> {

    @Override
    @JmsListener(destination = "${custom.activemq.foo-topic}", containerFactory = "jmsListenerContainerTopic")
    public void subscript(String msg) {
        System.out.println("此订阅者2被通知了消息" + msg);
    }
}
