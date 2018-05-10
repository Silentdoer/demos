package me.silentdoer.springbootvalidannotation;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SpringbootValidAnnotationApplication {

    /**
     * TODO 经过测试确实替代了默认的Jackson
     * 注：Fastjson支持将数值字符串转换为数值，也支持将数值转换为字符串
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2:添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        /**
         * TODO 第一个SerializerFeature.PrettyFormat可以省略，毕竟这会造成额外的内存消耗和流量，第二个是用来指定当属性值为null是是否输出：pro:null
         */
        fastJsonConfig.setSerializerFeatures(SerializerFeature.SkipTransientField);
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
        return new HttpMessageConverters(converter);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootValidAnnotationApplication.class, args);
    }

    private static void test(Map map){
        final Map<String, String> m2 = map;
        m2.put("uuu", "sss");
        System.out.println(m2.size());
    }
}
