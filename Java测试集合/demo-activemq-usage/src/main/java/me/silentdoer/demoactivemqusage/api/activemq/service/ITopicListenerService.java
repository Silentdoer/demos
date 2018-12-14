package me.silentdoer.demoactivemqusage.api.activemq.service;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/13/2018 11:32 AM
 */
public interface ITopicListenerService<T> {

    /**
     * 订阅
     * @param msg
     */
    void subscript(T msg);
}
