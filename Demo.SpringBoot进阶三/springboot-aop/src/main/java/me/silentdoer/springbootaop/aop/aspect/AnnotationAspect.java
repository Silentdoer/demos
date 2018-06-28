package me.silentdoer.springbootaop.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootaop.aop.support.BeforeService;
import me.silentdoer.springbootaop.component.ApiBefore;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

/**
 * TODO 这里面可以有多个@Before，也就是说本质上是将方法作为Advice，Aspect类只是一个借助对象
 * @author liqi.wang
 * @version 1.0.0
 */
@Aspect
@Service
@Slf4j
public class AnnotationAspect implements ApplicationContextAware {
    private ApplicationContext applicationContext;

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

    /**
     * 对于 @ApiSession 而言必须要用around，因为proceed(..)里可以用其它参数，使得自己可以手动修改原本的参数，而before修改了参数后无法显式指定（getArgs说不定只是备份对真正的方法调用的参数无影响
     * @param jp
     * @param apiBefore
     */
    @Before("@annotation(me.silentdoer.springbootaop.component.ApiBefore) && @annotation(apiBefore)")
    public void before(JoinPoint jp, ApiBefore apiBefore){
        log.info("{},before,values:{},args:{}", jp.getSignature().getName(), Arrays.asList(apiBefore.value()), jp.getArgs());
        for(Class<?> clazz : apiBefore.value()){
            /**
             * 用在@ApiBefore(Xx.class)的Xx需要@Service且默认singleton
             */
            Object bean = this.applicationContext.getBean(clazz);
            if(bean instanceof BeforeService){
                ((BeforeService) bean).before(jp);
            }
        }
    }

    /**
     * TODO 注意，只要是bean就可以进行aop，而不是只能是controller
     * @param pjp
     */
    @Around("pointcutMapping()")
    public void aroundMapping(ProceedingJoinPoint pjp){
        MethodSignature signature = ((MethodSignature) pjp.getSignature());
        Method method = signature.getMethod();
        if(method.isAnnotationPresent(ApiBefore.class)){
            ApiBefore apiBefore = method.getDeclaredAnnotation(ApiBefore.class);
            try{
                // TODO 调用另一个 advice
                this.before(pjp, apiBefore);
            }catch (Exception ex){
                throw new IllegalStateException("本应该是ApiException的");
            }
        }
        // TODO 继续执行around业务，如判断参数是否有@ApiSession且判断required
        /*if (!apiSession.required()) {
            continue;
        }*/
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
