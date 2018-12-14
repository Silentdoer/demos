package me.silentdoer.demoactivemqusage.api.activemq.service.impl;

import me.silentdoer.demoactivemqusage.api.activemq.service.IQueueConsumerService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * 这个本来是用来监听普通订单队列的，但是这里为了测试一下点对点模式只有一个消费者能消费某条消息，故临时写成一样，
 * 经过测试确实是点对点的Queue（非Topic）只会被一个消费者消费（但是queue和topic的消费者/订阅者的注解都是@JmsListener）
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/12/2018 8:28 PM
 */
@Service
public class OrderQueueConsumerServiceImpl implements IQueueConsumerService<String> {

    @Override
    @JmsListener(destination = "${custom.activemq.background-order-queue}", containerFactory = "jmsListenerContainerQueue")
    public void receive(String msg) {
        System.out.println("已经将普通的订单进行了处理" + msg);
    }
}
