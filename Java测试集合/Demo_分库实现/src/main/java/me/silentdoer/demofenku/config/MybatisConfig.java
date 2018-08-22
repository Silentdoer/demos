package me.silentdoer.demofenku.config;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demofenku.support.db.DataSourceTypeEnum;
import me.silentdoer.demofenku.support.db.MyRoutingDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
public class MybatisConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("${mysql.datasource.configLocation}")
    private String configLocation;

    @Resource(name = "writeDataSource")
    private DataSource writeDataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() {
        log.info("--------------------  sqlSessionFactory init  ---------------------");
        try {
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSourceComposite());
            //设置mybatis-config.xml配置文件位置
            sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));

            return sessionFactoryBean.getObject();
        } catch (Exception e) {
            log.error("{}", e.toString());
        }
        return null;
    }

    /**
     * 把所有数据库都放在路由中
     */
    @Bean(name = "dataSourceComposite")
    public AbstractRoutingDataSource dataSourceComposite() {

        Map<Object, Object> targetDataSources = new HashMap<Object, Object>(4);
        //把所有数据库都放在targetDataSources中,注意key值要和determineCurrentLookupKey()中代码写的一至，
        // 配置写库/主库
        targetDataSources.put(DataSourceTypeEnum.WRITE.getType(), this.writeDataSource);

        Map<String, DataSource> beansOfType = this.applicationContext.getBeansOfType(DataSource.class);

        List<DataSource> readDataSources = beansOfType.entrySet().stream().filter(e -> {
            return e.getKey().contains("read") || e.getKey().contains("slave");
        }).map(Map.Entry::getValue).collect(Collectors.toList());

        if (readDataSources.size() < 1) {
            throw new IllegalStateException("读写分离项目里至少需要配置一个读库");
        }
        MyRoutingDataSource dynamicDataSource = new MyRoutingDataSource();
        dynamicDataSource.setReadDataSourceSize(readDataSources.size());

        // 配置多个读库/从库
        for (int i = 1; i <= readDataSources.size(); i++) {
            String targetKey = DataSourceTypeEnum.READ.getType() + i;
            targetDataSources.put(targetKey, readDataSources.get(i - 1));
        }

        //默认库（当通过targetKey从targetDataSources里找不到时使用的默认DataSource）
        dynamicDataSource.setDefaultTargetDataSource(this.writeDataSource);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSourceComposite());
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}