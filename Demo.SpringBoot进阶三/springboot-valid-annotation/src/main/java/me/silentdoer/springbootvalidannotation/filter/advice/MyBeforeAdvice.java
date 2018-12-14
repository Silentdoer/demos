package me.silentdoer.springbootvalidannotation.filter.advice;

import org.aspectj.lang.JoinPoint;
import org.springframework.aop.BeforeAdvice;

/**
 * 用于替代Spring的MethodBeforeAdvice
 * @author silentdoer
 * @version 1.0
 */
public interface MyBeforeAdvice extends BeforeAdvice {
    void doBefore(JoinPoint jp) throws Exception;
}
