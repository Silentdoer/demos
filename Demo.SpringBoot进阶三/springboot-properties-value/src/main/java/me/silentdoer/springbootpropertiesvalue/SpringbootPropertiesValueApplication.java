package me.silentdoer.springbootpropertiesvalue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringbootPropertiesValueApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootPropertiesValueApplication.class, args);
    }
}
