package me.silentdoer.springbootcustomvalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
public class SpringbootCustomValidatorApplication {

    /**
     * TODO 没有这个 那么直接在mapping方法参数前加@Min之类的不会生效
     * @return
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCustomValidatorApplication.class, args);
    }
}
