package me.silentdoer.demousagerabbitmq;

import me.silentdoer.demousagerabbitmq.model.User;
import me.silentdoer.demousagerabbitmq.service.IMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoUsageRabbitmqApplicationTests {

    @Resource
    private IMessageService messageService;

    @Test
    public void contextLoads() throws InterruptedException {
        //this.messageService.sendMsg("test_queue_1", "hello i am delay msg");
        User user = new User();
        user.setId(3L);
        user.setName("见就看到");
        this.messageService.sendMsg("test_queue_1", user);
        TimeUnit.SECONDS.sleep(5);
    }
}