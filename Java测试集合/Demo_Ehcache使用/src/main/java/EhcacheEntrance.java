import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.util.Map;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/24/2018 10:01 AM
 */
public class EhcacheEntrance {

    public static void main(String[] args) {

        /*
        withCache表示创建CacheManager的同时，立刻创建并管理一个Cache（这里其实是这种结构，CacheManager是一个Map容器，它管理Cache
        ，而Cache又是Map结构，它管理我们真正的数据；（因此CacheManager就类似Redis管理Hash）
         */
        CacheManager manager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(Long.class, String.class,
                                                      ResourcePoolsBuilder.heap(10))).build();

        manager.init();

        // Cache其实就类似一个HashMap，Key是Long类型，Value是String类型
        Cache<Long, String> preConfigured = manager.getCache("preConfigured", Long.class, String.class);

        preConfigured.put(3L, "双方都");

        System.out.println(preConfigured.get(3L));
    }
}
