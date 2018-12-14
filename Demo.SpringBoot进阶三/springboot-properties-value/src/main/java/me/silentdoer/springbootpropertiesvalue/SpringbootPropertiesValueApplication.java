package me.silentdoer.springbootpropertiesvalue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class SpringbootPropertiesValueApplication {

    @Bean
    public String testBean(){
        return "时间偶发的";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootPropertiesValueApplication.class, args);
    }
}
