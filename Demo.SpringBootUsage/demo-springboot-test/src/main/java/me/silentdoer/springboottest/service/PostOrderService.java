package me.silentdoer.springboottest.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 这里就不实现接口了
 * @author silentdoer
 * @version 1.0
 */
@Service("postOrderService")
public class PostOrderService {
    /**
     * 被@Async注解的方法在执行时会作为task提交到线程池里执行；
     * 没有自定义一个Executor实现类的Bean的话那么默认会用SimpleAsyncTaskExecutor来包装并异步执行这个方法；
     * 要自定义Executor可以参考：https://blog.csdn.net/clementad/article/details/53607311
     * @param order
     */
    @Async
    public void processNormalOrder(Object order){
        System.out.println(String.format("当前task所在线程是：%s", Thread.currentThread().getName()));
    }
}
