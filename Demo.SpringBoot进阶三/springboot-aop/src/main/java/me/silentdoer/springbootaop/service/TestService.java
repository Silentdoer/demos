package me.silentdoer.springbootaop.service;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootaop.component.ApiBefore;
import me.silentdoer.springbootaop.component.NeedLogin;
import org.springframework.stereotype.Service;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Service
@Slf4j
public class TestService {

    public void service1(){
        log.info("service1");
    }

    public Long service2(int num, String name){
        log.info("service2:{},{}", num, name);
        return -1L;
    }

    /**
     * TODO 这个本来应该在mapping方法上面的，这里是为了测试准确
     */
    @ApiBefore(NeedLogin.class)
    public void service3(int ab){
        log.info("service3,{}", ab);
    }

    public void service4(int a, String b){
        log.info("service4,{},{}", a, b);
    }
}
