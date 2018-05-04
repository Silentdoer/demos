package me.silentdoer.springbootmybatis.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

/**
 * 暂且不做
 * @author silentdoer
 * @version 1.0
 */
@Service
@Slf4j
// 要加spring-boot-starter-aop
@Aspect
public class LogAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void pointcut1(){
    }

    /**
     * 注意，每个@Before都是一个最小单元，因此一个@Aspect里有多个@Before是没有关系的，假设java有真委托类型的话那么其实@Before都可以直接注解到委托变量上（即@Aspect可以不要）
     * 所有的有@GetMapping注解的方法只要其所属对象也是IOC中的一个bean那么spring就会进行代码织入
     * @param jp
     */
    @Before("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void before(JoinPoint jp){
        log.info(StringUtils.join("为方法", jp.getSignature().getName(), "织入了Before代码"));
    }

    @After("pointcut1()")
    public void after(JoinPoint jp){
        log.info(StringUtils.join("为方法", jp.getSignature().getName(), "织入了After代码"));
    }
}
