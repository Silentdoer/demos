package me.silentdoer.springboottest.aop.advice;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author silentdoer
 * @version 1.0
 */
@Slf4j
@Aspect
@Service
public class LogAspect {
    /**
     * 定义通用的pointcut1
     */
    //@Pointcut(value="execution(* bean.HelloApi.aspectTest(..)) && args(a1,b2)",argNames="a1,b2")
    //public void pointcut1(String a1,String b2){}

    /**
     * 下面两个测试已经成功
     */
    /*@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void myFunc() { }

    @Before("myFunc()")
    public void fBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String msg = StringUtils.join(methodName, "I", ',');
        System.out.println(msg);
        log.info(msg);
    }*/

    /**
     * 可以用@Before(value="pointcut1(a,b)",argNames="a,b")来引用上面的通用的pointcut1
     * 注意，最后一个是方法，也就是说倒数第二个是类，之前最后少了.*导致spring认为service这个包是类名
     * 现在测试成功
     * @param jp
     */
//    @Before("execution(* me.silentdoer.springboottest.service.AOPTestService.*(..))")
    public void before(JoinPoint jp) {
        System.out.println(String.format("%s类的before被执行", LogAspect.class.getName()));
    }

    /**
     * OK
     * @param jp
     * @param result
     */
//    @AfterReturning(value = "execution(* me.silentdoer.springboottest.*.AOPTestService.*(..))", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
        System.out.println(String.format("%s类的%s被执行，返回值为:%s", LogAspect.class.getName(), jp.getSignature().getName(), result));
    }

    /**
     * OK
     */
//    @After("execution(* me.silentdoer.springboottest.service.AOPTestService.*(..))")
    public void after() {  // JoinPoint可要可不要
        System.out.println(String.format("%s类的after方法被执行", LogAspect.class.getName()));
    }

    /**
     * OK，而且经过测试After和Around可以共存，先执行完Around，接着又会执行after
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(* me.silentdoer.springboottest.service.AOPTestService.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println(String.format("%s类的around开始", LogAspect.class.getName()));
        Object result = pjp.proceed();
        System.out.println(String.format("%s类的around结束", LogAspect.class.getName()));
        return result;
    }
}
