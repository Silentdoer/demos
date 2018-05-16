package me.silentdoer.springbootasync.service;

import java.util.List;

/**
 * @author silentdoer
 * @version 1.0
 */
public interface TaskProducer {
    /**
     * 将客户端的请求生成MQ中的任务
     * @param task task
     */
    int offerTask(Object task);

    /**
     * 一次性offer多个task
     * @param tasks tasks
     */
    void offerTasks(List<Object> tasks);
}
