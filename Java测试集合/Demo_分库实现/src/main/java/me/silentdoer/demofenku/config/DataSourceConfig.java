package me.silentdoer.demofenku.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DataSourceConfig {

    /**
     * 写库 数据源配置，这里生成的bean由于@ConfigurationProperties的存在会调用bean.setXX方法来设置
     * 来源于配置文件里的以mysql.datasource.write开头的属性如xxx.write.test-mm则是bean.setTestMm(..)
     */
    @Bean(name = "writeDataSource")
    @Primary
    @ConfigurationProperties(prefix = "mysql.datasource.write")
    public DataSource writeDataSource() {
        log.info("-------------------- writeDataSource init ---------------------");
        return new DruidDataSource();
    }

    /**
     * 有多少个从库就要配置多少个，不过这个配置其实最好配置在xml里，因为这个不像controller写好了基本上不会变
     */
    @Bean(name = "readDataSource1")
    @ConfigurationProperties(prefix = "mysql.datasource.read1")
    public DataSource readDataSourceOne() {
        log.info("-------------------- read1 DataSourceOne init ---------------------");
        return new DruidDataSource();
    }

    @Bean(name = "readDataSource2")
    @ConfigurationProperties(prefix = "mysql.datasource.read2")
    public DataSource readDataSourceTwo() {
        log.info("-------------------- read2 DataSourceTwo init ---------------------");
        return new DruidDataSource();
    }
}