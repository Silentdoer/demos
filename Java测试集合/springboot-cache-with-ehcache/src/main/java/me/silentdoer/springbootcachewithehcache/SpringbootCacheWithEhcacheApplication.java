package me.silentdoer.springbootcachewithehcache;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.support.SimpleCacheManager;

@SpringBootApplication
public class SpringbootCacheWithEhcacheApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCacheWithEhcacheApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
