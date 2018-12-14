package me.silentdoer.demoactivemqusage.api.activemq.service;

import java.io.Serializable;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/12/2018 8:15 PM
 */
public interface IQueueProducerService<T extends Serializable> {

    void send(T msg, String name, long delayTime);
}
