package me.silentdoer.demosimpleproj.core.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/12/2018 4:28 PM
 */
@Slf4j
@Configuration
public class ConverterConfig {

    /**
     * 使用于对@RequestBody和@ResponseBody的处理，本地统一一种格式即可
     *
     * @return HttpMessageConverters
     */
    @Bean
    public HttpMessageConverters customConverters() {
        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        // 要有否则会自动转换如#这些字符
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        gsonHttpMessageConverter.setGson(gson);
        messageConverters.add(gsonHttpMessageConverter);
        // 默认优先用的自定义的，因此优先用Gson，不需要自己去配置顺序
        return new HttpMessageConverters(true, messageConverters);
    }
}