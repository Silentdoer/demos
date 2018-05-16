package me.silentdoer.springbootaop.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Parameter;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Aspect
@Service
@Slf4j
public class MethodPathAspect {

    /**
     * 另一个是@annotation(..)
     * 其中void可以变成*来表达所有的返回值类型，service1可以用*进行通配，()可以变成(..)进行通配
     */
    @Pointcut("execution(void me..*.TestService.service1())")
    public void pointcut(){
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String name = pjp.getSignature().getName();
        log.info("{},around before", name);
        /**
         * 这里还可以对方法的参数进行判断是否有什么特殊注解，如@ApiSession
         */
        Object result = pjp.proceed();
        // 这个是将要作为参数值来调用真正的方法，这里可以做预处理
        //pjp.getArgs()
        // TODO 通过Parameter对象可以判断某方法参数上有什么注解
        //Parameter[] parameters = ((MethodSignature) pjp.getSignature()).getMethod().getParameters();
        log.info("{},around after", name);  // name可以不要，那么{}就会照常输出
        return result;
    }

    /**
     * 方法后面的int,String中间可以有空格，它可以用..来通配，注意，参数类型写反了或少了也是不对应的（TODO 无论是参数类型还是返回值类型都必须完全对应，哪怕是对应的包装类或Object类）
     * @param jp
     */
    @Before("execution(Long me..*.TestService.*(int,String))")
    public void before(JoinPoint jp){
        log.info("方法{}before", jp.getSignature().getName());
    }
}
