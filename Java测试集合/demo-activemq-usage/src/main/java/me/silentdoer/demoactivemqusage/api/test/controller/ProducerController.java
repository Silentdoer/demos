package me.silentdoer.demoactivemqusage.api.test.controller;

import com.alibaba.fastjson.JSON;
import me.silentdoer.demoactivemqusage.api.test.model.dto.BgOrder;
import me.silentdoer.demoactivemqusage.api.test.model.dto.Order;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.validation.Valid;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/12/2018 6:08 PM
 */
@RestController
@RequestMapping("/api/test/activemq/produce")
public class ProducerController {

    @Resource
    private JmsMessagingTemplate jms;

    @Resource(name = "orderQueue")
    private Queue orderQueue;

    @Resource(name = "bgOrderQueue")
    private Queue bgOrderQueue;

    @Resource(name = "fooTopic")
    private Topic fooTopic;

    @PostMapping("/publishOrder")
    public String publishOrder(@RequestBody(required = true) @Valid Order order) {
        jms.convertAndSend(this.orderQueue, JSON.toJSONString(order));
        return "order发送成功";
    }

    @PostMapping("/publishBgOrder")
    public String publishBgOrder(@RequestBody(required = true) @Valid BgOrder order) {
        jms.convertAndSend(this.bgOrderQueue, JSON.toJSONString(order));
        return "bgOrder发送成功";
    }

    /**
     * 暂未实现
     * @param foo
     * @return
     */
    @PostMapping("/publishTopic")
    public String publishTopic(@RequestBody(required = true) @Valid String foo) {
        jms.convertAndSend(this.fooTopic, foo);
        return "topic发送成功";
    }
}
