package me.silentdoer.demofenku.support.db;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/20/2018 3:43 PM
 */
@Slf4j
@Aspect
@Service
public class RoutingDataSourceAspect {

    @Resource(name = "dataSourceComposite")
    private DataSource dynamicDataSource;

    private List<String> readMethodPattern = new ArrayList<>(8);

    {
        readMethodPattern.addAll(Arrays.asList("select", "get", "count", "find", "query", "list"));
    }

    @Pointcut(value = "execution(* me.silentdoer.demofenku..*.dao.*Mapper.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void setDataSource(JoinPoint jp) {

        Object target = jp.getTarget();
        String methodName = jp.getSignature().getName();
        Class<?> mapperClazz = target.getClass().getInterfaces()[0];
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        try {
            // 逻辑是Mapper方法上有ReadDataSource注解则走读库，有WriteDataSource走写库，如果没有则默认走写库
            // 这里如果涉及事务不用担心，由于存在@Transactional那么在service层就已经通过路由DataSource获得了默认的（写库）
            // 所以事务走的都是写库
            Method m = mapperClazz.getMethod(methodName, parameterTypes);
            // TODO 这里解释一下，由于如果存在@Transactional那么是比Mapper的Aop拦截要早，因此获得的是DefaultTarget，所以
            // 这里DefaultTarget是读库，因此判断DataSource当前线程是否处于事务中，如果是则这里就忽略设置步骤（这里用writeDataSource没用）
            ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dynamicDataSource);
            // 写库的DataSource当前线程处于事务里，说明是开启了事务
            if (Objects.nonNull(conHolder) && conHolder.isSynchronizedWithTransaction()) {
                // TODO 其实这里可以不用set，因为DataSource已经在service层确立
                DataSourceTypeHolder.setWrite();
            } else if (m != null && m.isAnnotationPresent(ReadDataSource.class)) {
                DataSourceTypeHolder.setRead();
            } else if(m != null && m.isAnnotationPresent(WriteDataSource.class)) {
                DataSourceTypeHolder.setWrite();
            } else if(this.readMethodPattern.stream().anyMatch(methodName::startsWith)) {
                DataSourceTypeHolder.setRead();
            } else {
                DataSourceTypeHolder.setWrite();
            }
        } catch (Exception e) {
            // ignored
        }
    }

    /**
     * 在Mapper方法结束后清除DataSource的设置
     */
    @AfterReturning(value = "pointCut()")
    public void after() {
        DataSourceTypeHolder.clearTypeSetting();
    }
}