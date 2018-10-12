package me.silentdoer.demoshardbatisfenbiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackageClasses = {me.silentdoer.demoshardbatisfenbiao.api.dao.UserSignedHistoryMapper.class})
public class DemoShardbatisFenbiaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoShardbatisFenbiaoApplication.class, args);
    }
}
