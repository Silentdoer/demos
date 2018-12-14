package me.silentdoer.demolocaldevprodconfigload.config;

import me.silentdoer.demolocaldevprodconfigload.util.DirectoryUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/13/2018 4:30 PM
 */
@Configuration
public class InfrastructureConfig {

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(Environment environment) throws IOException {
        final String configDir = environment.getProperty("custom.config.base.dir");
        Assert.notNull(configDir);
        final String configFileExtensions = "properties,yml";
        // 这里规定application.properties里的active只能有一个，因此只取第一个即可，值为如dev、prod、local等；
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        List<String> filePaths = DirectoryUtils.getAllFileNamesBaseDirWithSuffix(new ClassPathResource(configDir).getFile(), configFileExtensions);
        // setLocations是一次性设置所有，所以IMP里的是错误的用法
        propertyPlaceholderConfigurer.setLocations(filePaths.stream().map(FileSystemResource::new)
                                                           .collect(Collectors.toList()).toArray(new FileSystemResource[filePaths.size()]));
        return propertyPlaceholderConfigurer;
    }
}
