package me.silentdoer.mybatisinterceptorusage.support.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.*;

/**
 * 这里的@Intercepts是用来声明下面的拦截器的拦截策略，这个策略是一个数组；
 * 然后@Signature则是具体的每一个拦截策略的定义，这里是用来定义拦截的“方法”签名
 * <p>
 * 创建的Mybatis的Interceptor需要配置mybatis-config.xml里的plugins/plugin使得Mybatis会去装载这个Interceptor
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 11:11 AM
 */
@Intercepts
        ({
                 @Signature(type = Executor.class, method = "query",
                            args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
         })
@Slf4j
public class PaginateSqlInterceptor implements Interceptor {

    private String enable = "false";

    /**
     * 这是对应上面的args的序号
     */
    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    static int ROW_BOUNDS_INDEX = 2;
    static int RESULT_HANDLER_INDEX = 3;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        log.info("page in mybatis plugin 1");

        if (Objects.equals(this.enable, "false")) {
            log.info("Not enable");
            return invocation.proceed();
        }

        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement mappedStatement = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];
        final BoundSql boundSql = mappedStatement.getBoundSql(parameter);

        String sql = boundSql.getSql();

        log.info("Test:" + sql);
        // TODO 成功修改SQL语句，原来的语句是：select t.* from tb_student t where fd_name = ?
        // 但最终是要修改MappedStatement
        sql += " limit 1";

        // 重新new一个查询语句对像
        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        // 把新的查询放到statement里
        MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        // 将修改后的MappedStatement覆盖原来的参数
        queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
        return invocation.proceed();
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    public static class BoundSqlSqlSource implements SqlSource {

        private BoundSql boundSql;

        BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
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
     *
     * @param properties 来源于配置里的plugin的property
     */
    @Override
    public void setProperties(Properties properties) {

        log.info("in mybatis plugin 3");

        // FLAG 用来记录Mybatis用的是什么数据库，pagehelper可以指定这个值也可以自动获取，自动获取就是因为Mybatis本身就指定了这个值，
        // 因此pagehelper从mybatis里获取就是自动获取
        String dialect = properties.getProperty("dialect");
        log.info("mybatis intercept dialect:{}", dialect);
        this.enable = properties.getProperty("enable");
    }
}
