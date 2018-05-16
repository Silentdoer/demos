package me.silentdoer.springbootaop.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Aspect
@Slf4j
@Service
public class MethodPathHasArgAspect {

    @Pointcut("execution(* me..*.TestService.service4(..))")
    public void pointcut(){
    }

    /**
     * TODO 重要，原来args可以不写在@Pointcut里
     * TODO 如果没有argNames，那么默认外面传的值从左往右分别对应 a和b，但是如果要反过来对应看before2
     * @param jp
     * @param a
     * @param b
     */
    @Before("pointcut() && args(a,b)")
    public void before(JoinPoint jp, int a, String b){
        log.info("before,{},{},{}", jp.getSignature().getName(), a, b);
    }

    /**
     * TODO 注意a其实在service4里是int，因此这样肯定报错（好吧，没有报错，但是也没有匹配到这个before）
     * 注意 argNames里是要包括所有的参数名而不只是其中一个
     * TODO argNames的只是说是args里的名字可以任意，（看了下实在没发现argNames有什么用，这里是args和argNames和before的参数都必须对应），这里没有argNames结果一样
     * @param jp
     * @param aa
     * @param bb
     */
    @Before(value = "pointcut() && args(bb,aa)", argNames = "jp,aa,bb")
    public void before(JoinPoint jp, String aa, int bb){
        log.info("before2,{},{},{}", jp.getSignature().getName(), aa, bb);
    }
}
