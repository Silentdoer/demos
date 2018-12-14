package me.silentdoer.demoactivemqusage.api.activemq.service.impl;

import me.silentdoer.demoactivemqusage.api.activemq.service.IQueueProducerService;
import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.Resource;
import javax.jms.ObjectMessage;
import java.io.Serializable;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/12/2018 8:11 PM
 */
public class QueueProducerServiceImpl implements IQueueProducerService<Serializable> {

    @Resource
    private JmsTemplate jmsTemplate;

    @Override
    public void send(Serializable msg, String destinationName, long delayTime) {

        // Destination接口对象其实就是用来描述具体的queue或topic的；
        // 这里第二个参数是MessageCreator
        jmsTemplate.send(destinationName, session -> {
            ObjectMessage objectMessage = session.createObjectMessage(msg);
            if (delayTime > 0) {
                objectMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
            }
            return objectMessage;
        });
    }

}
