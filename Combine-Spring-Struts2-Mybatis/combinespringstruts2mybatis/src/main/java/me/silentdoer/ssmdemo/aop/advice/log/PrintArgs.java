package me.silentdoer.ssmdemo.aop.advice.log;

import org.aopalliance.aop.Advice;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.xml.DefaultNamespaceHandlerResolver;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/22/18 8:23 AM
 */
/*@Component
  @Aspect*/
public class PrintArgs {

    /*@Before("execution(* com.zhangguo.Spring052.aop02.Math.*(..))")*/
    public void before(JoinPoint jp){
        System.out.println("----------前置通知----------");
        // print what's the method name
        //System.out.println(jp.getSignature().getName());
        System.out.println(String.format("args is -> %s", Arrays.toString(jp.getArgs())));
    }

    // after shi xian yuan li he around bu yi yang, qian zhe shi zhi ru, hou zhe shi ti huan
    public void after(JoinPoint jp){
        System.out.println("----------最终通知----------");
    }

    // around bu neng he before huo after gong cun.
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Around begin: " + pjp.getSignature().getName());
        System.out.println(String.format("args is -> %s", Arrays.toString(pjp.getArgs())));
        Object result = pjp.proceed();  // ok, bu xu yao yong proceed(args), proceed() nei bu zi ji hui chu li.
        // tong guo getArgs he proceed(args) ke yi dui args jin xing lan jie chu li
        /*Object[] args = pjp.getArgs();
        args[0] = (long)args[0] + 1;
        pjp.proceed(args);*/
        System.out.println("Around end");
        return result;
    }
}
