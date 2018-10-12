package me.silentdoer.demousagerabbitmq.service;

import me.silentdoer.demousagerabbitmq.model.User;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/24/2018 11:10 AM
 */
public interface IMessageService {

    void sendMsg(String queueName, String msg);

    void sendMsg(String queueName, User user);
}
