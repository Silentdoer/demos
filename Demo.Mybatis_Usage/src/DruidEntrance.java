import com.alibaba.druid.pool.DruidDataSource;
import me.silentdoer.mybatisusage.datasource.DruidDataSourceFactory;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-8 15:16
 */
public class DruidEntrance {
    public static void main(String[] args){
        // 这个Map就是url、username、password之类的键值对。
        //com.alibaba.druid.pool.DruidDataSourceFactory.createDataSource(new HashMap());
        // 只有init()后才能用此DataSource，而每次getConnection都会先判断是否需要init，故如果new了DruidDataSource后没有设置
        // Properties就init会报错。
        //DataSource dataSource = new DruidDataSource(); // 提供getConnection(..)的两种重载方法。
        DruidDataSource dataSource = new DruidDataSource();
        //dataSource.setConnectProperties(new HashMap<String, String>());
        //dataSource.configFromPropety(new HashMap<>());
        //dataSource.setMaxActive();
    }
}
