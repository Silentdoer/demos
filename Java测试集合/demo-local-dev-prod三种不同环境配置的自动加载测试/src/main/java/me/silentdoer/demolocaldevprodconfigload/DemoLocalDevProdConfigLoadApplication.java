package me.silentdoer.demolocaldevprodconfigload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 之前的local-dev-prod的环境的配置是通过application-dev.yml和application-prod.yml。。这样写，显然所有配置都写三个文件很不好分类；
 * 后来就是写application
 */
@SpringBootApplication
public class DemoLocalDevProdConfigLoadApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoLocalDevProdConfigLoadApplication.class, args);
    }

}

