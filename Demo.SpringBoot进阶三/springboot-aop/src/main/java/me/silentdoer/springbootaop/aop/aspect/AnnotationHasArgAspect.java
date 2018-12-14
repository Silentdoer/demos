package me.silentdoer.springbootaop.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootaop.aop.support.BeforeService;
import me.silentdoer.springbootaop.component.ApiBefore;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Service
@Slf4j
@Aspect
public class AnnotationHasArgAspect implements ApplicationContextAware {
    private AbstractApplicationContext applicationContext;

    /**
     * argNames它是用来指定pointcut的方法的参数如service(int, long)，那么这个属性可以用来指定将第一个参数值赋给Before的第二个参数（@annotation后面也可以 && args(a,b)
     * TODO 就可以认为argNames是没有用的，但是&&args(..)是有用的，
     * @param apiBefore 第二个注解的值apiBefore和pointcut的方法的参数值是一样的，它会将方法上注解的@ApiBefore对象 赋值给apiBefore，这样就省得要通过method.getAnnotations()来获取了
     */
    @Pointcut(value = "@annotation(me.silentdoer.springbootaop.component.ApiBefore) && @annotation(apiBefore)")
    public void pointcutWithArg(ApiBefore apiBefore){
    }

    /**
     * 加一个ApiBefore其实只是为了方便获取，不用也可以获取此方法上的ApiBefore注解对象
     * 同理args(arg)也是可以不用的，这里也是为了方便获取，pointcut的arg和before的arg对应而非和service3的参数名对应
     * @param jp
     * @param apiBefore
     */
    @Before("pointcutWithArg(apiBefore) && execution(* me..*.TestService.*(..)) && args(arg)")
    public void before(JoinPoint jp, ApiBefore apiBefore, int arg){
        log.info("{},before,values:{},args:{}", jp.getSignature().getName(), Arrays.asList(apiBefore.value()), Collections.singletonList(arg));
        for(Class clazz : apiBefore.value()){
            /**
             * 用在@ApiBefore(Xx.class)的Xx需要@Service且默认singleton
             */
            Object bean = this.applicationContext.getBean(clazz);
            if(bean instanceof BeforeService){
                ((BeforeService) bean).before(jp);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (AbstractApplicationContext) applicationContext;
    }
}
