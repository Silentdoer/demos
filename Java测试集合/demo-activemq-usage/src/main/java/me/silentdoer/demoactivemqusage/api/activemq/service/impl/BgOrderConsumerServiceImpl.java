package me.silentdoer.demoactivemqusage.api.activemq.service.impl;

import me.silentdoer.demoactivemqusage.api.test.model.dto.BgOrder;
import me.silentdoer.demoactivemqusage.api.activemq.service.IQueueConsumerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/12/2018 8:31 PM
 */
@Service
public class BgOrderConsumerServiceImpl implements IQueueConsumerService<String> {

    /**
     * 不能动态配置destination？？（可以，如下，只不过IDEA没那么智能点不了）
     *
     * 这里查下为什么String换成BgOrder就不行了，是否需要什么注解？
     *
     * 然后就是containerFactory确实是需要的，如果没有那么是会报错的（至少是不够优化的，它有时会报错，有时又不知怎么又没错了）
     * @param msg
     */
    @Override
    @JmsListener(destination = "${custom.activemq.background-order-queue}", containerFactory = "jmsListenerContainerQueue")
    public void receive(String msg) {
        System.out.println("已经处理了后台的推单" + msg);
    }
}
