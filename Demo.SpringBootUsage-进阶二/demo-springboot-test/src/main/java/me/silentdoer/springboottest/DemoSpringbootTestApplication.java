package me.silentdoer.springboottest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 当要将spring boot项目发布到tomcat时最好继承SpringBootServletInitializer类
 * @author silentdoer
 */
@SpringBootApplication
@MapperScan(basePackages = {"me.silentdoer.springboottest.dao"})
@EnableScheduling
@EnableRetry
@EnableAsync
@EnableCaching
/*@PropertySource(value={"classpath:db.properties", "classpath:env.properties"}, ignoreResourceNotFound = true)
@ImportResource("classpath:spring/beans.xml")*/
public class DemoSpringbootTestApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringbootTestApplication.class, args);
	}

	/**
	 * 设置启动类，用于tomcat运行项目的入口，可以和main方法共存，具体完整配置可以参考：https://www.cnblogs.com/renshengruozhiruchujian/p/7799521.html
	 * @param builder
	 * @return
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DemoSpringbootTestApplication.class);
	}
}
