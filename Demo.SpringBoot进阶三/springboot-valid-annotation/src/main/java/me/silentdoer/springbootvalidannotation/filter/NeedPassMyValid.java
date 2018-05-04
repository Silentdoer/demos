package me.silentdoer.springbootvalidannotation.filter;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootvalidannotation.annotation.MyJustNumberValid;
import me.silentdoer.springbootvalidannotation.filter.advice.MyBeforeAdvice;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.regex.Pattern;

/**
 * MethodBeforeAdvice和AfterReturningAdvice的参数太罗嗦了，可以自己扩展
 *
 * @author silentdoer
 * @version 1.0
 */
@Slf4j
@Aspect
@Service
public class NeedPassMyValid implements MyBeforeAdvice {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void pointcut1(){}

    final Pattern JUST_NUMBER = Pattern.compile("^\\d*$");

    @Before("pointcut1()")
    @Override
    public void doBefore(JoinPoint jp) throws Exception {
        Object[] args = jp.getArgs();
        MethodSignature signature = ((MethodSignature) jp.getSignature());
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        for (int i=0; i<parameters.length;i++){
            Parameter param = parameters[i];
            if(param.isAnnotationPresent(MyJustNumberValid.class)){
                log.info("执行到了自定义的方法参数拦截并开始判断是否符合");
                if(!param.getType().equals(String.class)){
                    throw new IOException("@MyNotNumberValid只能注解在String类型的变量上");
                }
                if(!JUST_NUMBER.matcher(args[i].toString()).matches()){
                    throw new IllegalArgumentException(param.getDeclaredAnnotation(MyJustNumberValid.class).message());
                }
            }
        }
    }
}
