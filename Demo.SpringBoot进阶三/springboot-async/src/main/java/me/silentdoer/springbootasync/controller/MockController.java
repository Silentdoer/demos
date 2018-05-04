package me.silentdoer.springbootasync.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootasync.service.TaskProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author silentdoer
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/mock")
public class MockController {
    /**
     * `@Resource`和@Autowire一样都是不允许为null的，但是@Resource注解的字段默认先按名称从IOC容器里找bean，找不到再按类型，忽略这里的一点时间消耗用@Resource即可
     * 而且@Resource是javax标准里的，对spring有一定的解耦合，可以指定bean的名称，如果没有指定则按字段名作为id从IOC容器里找。
     */
    @Resource
    private TaskProducer taskProducer;

    /**
     * 经过测试这个方法的输出为：
     * 当前方法所在的线程名为：http-nio-8090-exec-1
     * 当前方法所在的线程名为：SimpleAsyncTaskExecutor-1，方法为offerTask
     * 当前方法所在的线程名为：SimpleAsyncTaskExecutor-1，方法为offerTasks
     * 结论：1.由上面的输出可知@Async的方法确实是异步调用了不然http-xxx的会最后输出；
     * 2.当一个类里的两个@Async的方法之间存在互相调用时，被调用的方法不会生成异步调用
     * @return String
     */
    @RequestMapping("/test1")
    public String test1(){
        taskProducer.offerTask(new Object());
        log.info(String.format("当前方法所在的线程名为：%s", Thread.currentThread().getName()));
        JSONObject result = new JSONObject();
        // success
        result.put("state", 1);
        result.put("msg", "success");
        return result.toJSONString();
    }
}
