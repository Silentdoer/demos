package me.silentdoer.springboottest.service;

import org.springframework.stereotype.Service;

/**
 * @author silentdoer
 * @version 1.0
 */
@Service
public class AOPTestService {
    public Long show(){
        System.out.println("测试注解的AOP功能");
        return 88L;
    }
}
