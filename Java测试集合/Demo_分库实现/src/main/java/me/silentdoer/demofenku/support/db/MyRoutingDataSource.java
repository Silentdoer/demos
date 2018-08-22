package me.silentdoer.demofenku.support.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动态的DataSource，这里的DataSource其实是一个DataSource的组合器，它和其他DataSource一样（如DruidDataSource）提供
 * getConnection() 接口，同时它也是真正能够产生Connection对象的DataSource的容器，通过内部的targetDataSources存储，
 * 因此它的原理是：外部调用AbstractRoutingDataSource的getConnection()方法，这个方法内又通过determineCurrentLookupKey()方法
 * 来获得一个targetKey，通过这个key来在其targetDataSources的Map里找到真正的DataSource对象（如果没有对应的value则会用
 * DefaultTargetDataSource），然后用这个DataSource对象的getConnection()方法来产生连接对象返回给调用者
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/22/2018 10:58 AM
 */
@Slf4j
public class MyRoutingDataSource extends AbstractRoutingDataSource {

    private AtomicInteger count = new AtomicInteger(0);

    private Integer readDataSourceSize = 1;

    /**
     * 当执行的是开启了事务的service时，那其实还没到达Mapper时就已经开始通过AbstractRoutingDataSource的getConnection()里的
     * 此方法来路由真正的DataSource，所以此时的DataSourceTypeHolder.getCurrentDataSourceType()得到的值是null
     */
    @Override
    protected Object determineCurrentLookupKey() {

        String typeKey = DataSourceTypeHolder.getCurrentType();
        // 说明是开启了事务从而导致没有到达Mapper就已经开始getConnection()了
        if (Objects.isNull(typeKey)) {
            // 在AbstractRoutingDataSource的targetDataSources里没有这个key，从而用默认的写库
            return DataSourceTypeEnum.NULL_DATA_SOURCE.getType();
        }
        // 如果从MyRoutingDataSource的targetDataSources找不到此key那么就会用default，所以不需要判断这个key是否是null
        if (DataSourceTypeEnum.WRITE.getType().equals(typeKey)) {
            // 是主库直接return这个key即可
            return DataSourceTypeHolder.getCurrentType();
        }

        // 读库， 简单负载均衡；配置从1开始，为1、2、3.。。100这样顺延，不做01、02这种操作（主要是没什么意义）
        // 多个读库和写库的表结构、数据等是一致的（有一定延迟）
        int number = count.getAndAdd(1);
        // 范围：0 - (readDataSourceSize - 1)
        int routingKey = number % readDataSourceSize + 1;
        return typeKey + routingKey;
    }

    public void setReadDataSourceSize(Integer readDataSourceSize) {
        this.readDataSourceSize = readDataSourceSize;
    }
}