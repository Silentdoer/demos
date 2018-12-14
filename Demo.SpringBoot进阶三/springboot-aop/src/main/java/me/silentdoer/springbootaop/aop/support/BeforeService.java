package me.silentdoer.springbootaop.aop.support;

import org.aspectj.lang.JoinPoint;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public interface BeforeService {
    void before(JoinPoint jp);
}
