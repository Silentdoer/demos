package me.silentdoer.springbootmybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author silentdoer
 * @version 1.0
 */
@MapperScan(basePackages = "me.silentdoer.springbootmybatis.dao")
@Configuration
public class MapperConfig {
}
