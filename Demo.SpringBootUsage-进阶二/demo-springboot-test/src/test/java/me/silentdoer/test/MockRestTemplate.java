package me.silentdoer.test;

import me.silentdoer.springboottest.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

/**
 * @author silentdoer
 * @version 1.0
 */
public class MockRestTemplate {
    /**
     * 用于测试RestTemplate，有RestTemplate的一方在调用这个对象时是作为Consumer（客户端）而存在；
     * restTemplate是用来访问REST服务而存在的，RestTemplate有点像HttpClient
     * 初次使用可以参考：https://blog.csdn.net/weixin_38266411/article/details/70046718
     */
    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args){
        Student student0 = new Student();
        student0.setName("uuu");
        student0.setUid(1L);
        student0.setGender('f');
        // 通过restTemplate调用RESTful的接口，它内部有很多api，其中postForObject的三个参数非别代表：请求地址、请求参数、HTTP响应转换被转换成的对象类型
        // 这里也是通过HttpMessageConverter将student0转换为了字符串；
        Student student = restTemplate.postForObject("http://localhost:8090/mock/test7", student0, Student.class);
        System.out.println(student);
    }
}
