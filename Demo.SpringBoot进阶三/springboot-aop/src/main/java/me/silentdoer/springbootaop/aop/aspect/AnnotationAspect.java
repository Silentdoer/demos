package me.silentdoer.springbootaop.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

/**
 * TODO 这里面可以有多个@Before，也就是说本质上是将方法作为Advice，Aspect类只是一个借助对象
 * @author liqi.wang
 * @version 1.0.0
 */
@Aspect
@Service
@Slf4j
public class AnnotationAspect {

    /**
     * 先写pointcut()再写@Pointcut，否则不提示
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void pointcutMapping(){
    }

    @Pointcut("@annotation(me.silentdoer.springbootaop.component.ApiBefore)")
    public void pointcutApiBefore(){
    }

    /**
     * 如果有多个pointcut这里还可以用&&
     * @param jp
     */
    @Before("pointcutMapping()")
    public void beforeMapping(JoinPoint jp){
        log.info("Mapping在方法{}之前", ((MethodSignature) jp.getSignature()).getMethod());
    }

    /**
     * TODO 重要，当某个方法上有@GetMapping和@ApiBefore时，那么优先用这个处理（精确匹配），但是之后仍然会由上面的beforeMapping处理
     * TODO 即这里类似tomcat的filter而非servlet
     * @param jp
     */
    @Before("pointcutMapping() && pointcutApiBefore()")
    public void beforeApiBefore(JoinPoint jp){
        log.info("ApiBefore在方法{}之前", ((MethodSignature) jp.getSignature()).getMethod());
    }
}
