package me.silentdoer.mybatisinterceptorusage.support.mybatis.interceptor;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 3:03 PM
 */
@Intercepts(
        {
                // type是一个具体的委托对象，它“存储着”多套策略逻辑/执行方法，而这里具体要拦截type的哪个方法通过method和args来指定
                // type 是 target，然后method是target.prepare(args)这样的匹配逻辑
                /*
                这里匹配到的StatementHandler的方法是：
                Statement prepare(Connection connection, Integer transactionTimeout) throws SQLException;
                 */
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
        }
)
@Slf4j
public class SqlPrintInterceptor2 implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("in mybatis plugin 11");

        // FLAG 由于我们的签名（拦截规则）里指定了type是StatementHandler，所以这里可以直接强制转换而不需要先判断类型
        Executor executor = (Executor) invocation.getTarget();
        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object parameter = args[1];
        BoundSql boundSql = statement.getBoundSql(parameter);

        var additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
        additionalParametersField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Object> parameterT = (Map<String, Object>) additionalParametersField.get(boundSql);
        System.err.println(parameterT.toString());

        log.info("mybatis intercept 11 sql:{}", boundSql.getSql());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 从配置里获取数据，如果不需要配置里的数据那么这里可以忽略
    }
}
