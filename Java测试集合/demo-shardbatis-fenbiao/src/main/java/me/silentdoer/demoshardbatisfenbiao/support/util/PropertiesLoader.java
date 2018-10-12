package me.silentdoer.demoshardbatisfenbiao.support.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/15/2018 10:14 AM
 */
@Slf4j
public class PropertiesLoader {

    /**
     * DefaultResourceLoader在加载文件时默认的路径就是resources路径，所以文件路径参数可以直接是properties/param.properties
     * 但最好还是用classpath:properties/param.properties（但是不要用/properties/param.properties）
     */
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

    private final Properties properties;

    public PropertiesLoader(String... resourcesPaths) {
        properties = loadProperties(resourcesPaths);
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * 取出Property。
     */
    private String getValue(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        return properties.getProperty(key);
    }

    /**
     * 取出String类型的Property,如果都為Null则抛出异常.
     */
    public String getProperty(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
    }

    /**
     * 取出String类型的Property.如果都為Null則返回Default值.
     */
    public String getProperty(String key, String defaultValue) {
        String value = getValue(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 取出Integer类型的Property.如果都為Null或内容错误则抛出异常.
     */
    public Integer getInteger(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Integer.valueOf(value);
    }

    /**
     * 取出Integer类型的Property.如果都為Null則返回Default值，如果内容错误则抛出异常
     */
    public Integer getInteger(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Integer.valueOf(value) : defaultValue;
    }

    /**
     * 取出Double类型的Property.如果都為Null或内容错误则抛出异常.
     */
    public Double getDouble(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Double.valueOf(value);
    }

    /**
     * 取出Double类型的Property.如果都為Null則返回Default值，如果内容错误则抛出异常
     */
    public Double getDouble(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Double.valueOf(value) : defaultValue;
    }

    /**
     * 取出Boolean类型的Property.如果都為Null抛出异常,如果内容不是true/false则返回false.
     */
    public Boolean getBoolean(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Boolean.valueOf(value);
    }

    /**
     * 取出Boolean类型的Propert.如果都為Null則返回Default值,如果内容不为true/false则返回false.
     */
    public Boolean getBoolean(String key, boolean defaultValue) {
        String value = getValue(key);
        return value != null ? Boolean.valueOf(value) : defaultValue;
    }

    /**
     * 取出Boolean类型的Propert.如果Null則则抛出异常
     */
    public BigDecimal getBigDecimal(String key) {
        String value = getValue(key);
        if (StringUtils.isNotEmpty(value)) {
            return new BigDecimal(value);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * 取出Boolean类型的Propert.如果Null則返回Default值
     */
    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        String value = getValue(key);
        if (StringUtils.isNotEmpty(value)) {
            return new BigDecimal(value);
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 载入多个文件, 文件路径使用Spring Resource格式.
     */
    private Properties loadProperties(String... resourcesPaths) {
        Properties props = new Properties();

        for (String location : resourcesPaths) {
            InputStream is = null;
            try {
                // 这个也可以用PropertyResourceBundle.getBundle("param")来获取
                Resource resource = resourceLoader.getResource(location);
                is = resource.getInputStream();

                // 将多个.properties文件merge到一个Properties对象里，props.load(is);也可以
                propertiesPersister.load(props, new InputStreamReader(is, "UTF-8"));
            } catch (IOException ex) {
                log.warn("Could not load properties from path:" + location + "," + ex.getMessage());
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
        return props;
    }
}
