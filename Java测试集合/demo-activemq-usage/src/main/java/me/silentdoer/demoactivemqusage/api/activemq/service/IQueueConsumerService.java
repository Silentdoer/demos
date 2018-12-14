package me.silentdoer.demoactivemqusage.api.activemq.service;

import java.io.Serializable;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/12/2018 8:27 PM
 */
public interface IQueueConsumerService<T> {

    void receive(T msg);
}