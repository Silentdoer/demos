package me.silentdoer.springbootaop.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Arrays;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Service
@Slf4j
@Aspect
public class TestBeforeAfterAroundSequenceAspect {

    /**
     * 即便引用了DeleteMapping，这里仍然需要写全称
     * @param jp
     * @param deleteMapping
     */
    @Before("@annotation(org.springframework.web.bind.annotation.DeleteMapping) && @annotation(deleteMapping)")
    public void before(JoinPoint jp, DeleteMapping deleteMapping){
        log.info("Method:{},AnnotationValue:{},before", jp.getSignature().getName(), Arrays.asList(deleteMapping.value()));
    }

    @After("@annotation(org.springframework.web.bind.annotation.DeleteMapping) && @annotation(deleteMapping)")
    public void after(JoinPoint jp, DeleteMapping deleteMapping){
        log.info("Method:{},AnnotationValue:{},after", jp.getSignature().getName(), Arrays.asList(deleteMapping.value()));
    }

    @Around("@annotation(org.springframework.web.bind.annotation.DeleteMapping) && @annotation(deleteMapping)")
    public Object around(ProceedingJoinPoint pjp, DeleteMapping deleteMapping) throws Throwable {
        log.info("Method:{},AnnotationValue:{},around start", pjp.getSignature().getName(), Arrays.asList(deleteMapping.value()));
        Object result = pjp.proceed();
        log.info("Method:{},AnnotationValue:{},around end", pjp.getSignature().getName(), Arrays.asList(deleteMapping.value()));
        return result;
    }
}
