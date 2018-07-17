package me.silentdoer.mybatisinterceptorusage.support.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.*;

/**
 * 这里的@Intercepts是用来声明下面的拦截器的拦截策略，这个策略是一个数组；
 * 然后@Signature则是具体的每一个拦截策略的定义，这里是用来定义拦截的“方法”签名
 *
 * 创建的Mybatis的Interceptor需要配置mybatis-config.xml里的plugins/plugin使得Mybatis会去装载这个Interceptor
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 11:11 AM
 */
@Intercepts(
        {
                // type是一个具体的委托对象，它“存储着”多套策略逻辑/执行方法，而这里具体要拦截type的哪个方法通过method和args来指定
                // type 是 target，然后method是target.prepare(args)这样的匹配逻辑
                /*
                这里匹配到的StatementHandler的方法是：
                Statement prepare(Connection connection, Integer transactionTimeout) throws SQLException;
                 */
                @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
        }
)
@Slf4j
public class SqlPrintInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        log.info("in mybatis plugin 1");

        // FLAG 由于我们的签名（拦截规则）里指定了type是StatementHandler，所以这里可以直接强制转换而不需要先判断类型
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();


        log.warn(boundSql.getParameterMappings().size() + "TTTTTTTT");
        ParameterMapping parameterMapping = boundSql.getParameterMappings().get(0);
        log.warn(parameterMapping.getProperty() + "MMMMMMMMMM");
        log.warn(boundSql.hasAdditionalParameter(parameterMapping.getProperty()) + "UUUUUUUUUU");
        log.warn(boundSql.getParameterObject().toString());
        log.warn(boundSql.getParameterObject().getClass().toString());

        List<Object> realParams = new LinkedList<>();
        @SuppressWarnings("unchecked")
        Map<String, Object> params = (Map<String, Object>) boundSql.getParameterObject();
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            if (Objects.nonNull(params.get(mapping.getProperty()))) {
                realParams.add(params.get(mapping.getProperty()));
            }
        }


        String sql = boundSql.getSql();
        // 如果是只用了log4j，那么这里注意要先判断isEnableDebug()，否则字符串拼接是会消耗资源，但是最后却没有执行日志打印导致无谓消耗
        // 这里打印出来的是prepare的SQL：select t.* from tb_student t where fd_name = ?，如果需要获取到具体的包括参数值的SQL，那么type要换成Executor.class才行
        log.info("mybatis intercept sql:{}, params:{}", sql, realParams.toString());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {

        // TODO 经过测试target的类型目前有：
        // org.apache.ibatis.executor.CachingExecutor;  org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
        // org.apache.ibatis.executor.resultset.DefaultResultSetHandler;  org.apache.ibatis.executor.statement.RoutingStatementHandler
        log.info("in mybatis plugin 2：" + target.getClass());
        //org.apache.ibatis.scripting.defaults.DefaultParameterHandler
        // FLAG 这个似乎是固定写法，就是TargetProxy.bind(..)方法，而这个plugin方法就是Interceptor的register方法，intercept的Invocation就是MethodInvocation
        return Plugin.wrap(target, this);
    }

    /**
     * 这个方法是初始化时就会执行来初始化当前对象，而intercept等方法是要拦截到了符合条件的才执行
     * @param properties 来源于配置里的plugin的property
     */
    @Override
    public void setProperties(Properties properties) {

        log.info("in mybatis plugin 3");

        // FLAG 用来记录Mybatis用的是什么数据库，pagehelper可以指定这个值也可以自动获取，自动获取就是因为Mybatis本身就指定了这个值，
        // 因此pagehelper从mybatis里获取就是自动获取
        String dialect = properties.getProperty("dialect");
        log.info("mybatis intercept dialect:{}", dialect);
    }
}
