package me.silentdoer.mybatisusage.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-7 20:21
 */
public class DruidDataSourceFactory extends UnpooledDataSourceFactory {
    /*
    注意，PooledDataSourceFactory也是这样实现的：
    public class PooledDataSourceFactory extends UnpooledDataSourceFactory {
        public PooledDataSourceFactory() {
            this.dataSource = new PooledDataSource();
        }
    }
     */
    public DruidDataSourceFactory() {
        this.dataSource = new DruidDataSource();
    }
}
