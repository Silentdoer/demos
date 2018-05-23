package me.silentdoer.springbootwebsocket.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import me.silentdoer.springbootwebsocket.service.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用于模拟产生消息的Controller，真实情况里也可能是别的系统产生消息放入MQ，然后此应用检测到然后将其消费并包装为消息，通过websocket主动推送给客户端
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
@RequestMapping(name = "模拟产生给客户端推送的消息")
public class GenerateMsgController {

    @GetMapping("/produceMsg")
    public Map<String, Object> produceMsg(String msg){
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("test1", "AAAAAAA");
        result.put("test2", "BBBBBBBB");
        log.info("产生了一条新消息：{}，请及时将其推送给客户端。", msg);
        try {
            // TODO 只需要用静态方法即可？？，经过测试，这个消息确实发送给了h5的websocket客户端
            WebSocketServer.sendInfo(String.join("", "New msg:", msg));
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
