package me.silentdoer.springbootasync.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootasync.service.TaskProducer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author silentdoer
 * @version 1.0
 */
@Slf4j
@Service("taskProducer")
public class TaskProducerImpl implements TaskProducer {

    /**
     * 注：SimpleAsyncTaskExecutor-是线程池线程的前缀，后面的1表示线程的id，getName()唯一。
     * 当前方法所在的线程名为：SimpleAsyncTaskExecutor-1，方法为offerTask
     * 当前方法所在的线程名为：SimpleAsyncTaskExecutor-1，方法为offerTasks
     * 结论：由上面的输出可知一个bean中的多个@Async注解的方法之间互相调用那么被调用方是不会生成一个新的Executor对象来异步调用对应的方法；
     * 通过@Async的value来指定由哪个线程池来执行task
     * @param task task
     */
    @Async("myExecutor")
    @Override
    public int offerTask(Object task) {
        // 将task转换包装为命令对象，暂时认为task就是任务的命令对象
        log.info(String.format("当前方法所在的线程名为：%s，方法为%s", Thread.currentThread().getName(), "offerTask"));
        offerTasks(Collections.singletonList(task));
        return 888;
    }

    @Async("myExecutor")
    @Override
    public void offerTasks(List<Object> tasks){
        log.info(String.format("当前方法所在的线程名为：%s，方法为%s", Thread.currentThread().getName(), "offerTasks"));
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
