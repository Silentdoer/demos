package me.silentdoer.demoactivemqusage.api.activemq.service.impl;

import me.silentdoer.demoactivemqusage.api.activemq.service.ITopicListenerService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/13/2018 11:33 AM
 */
@Service
public class TopicListenerServiceImpl implements ITopicListenerService<String> {

    @Override
    @JmsListener(destination = "${custom.activemq.foo-topic}", containerFactory = "jmsListenerContainerTopic")
    public void subscript(String msg) {
        System.out.println("此订阅者被通知了消息" + msg);
    }
}
