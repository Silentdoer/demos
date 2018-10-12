package me.silentdoer.demousagerabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoUsageRabbitmqApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DemoUsageRabbitmqApplication.class, args);
        String a = "2018-11-01";
        String b = "2018-11-04";
        System.out.println(b.compareTo(a));
    }
}
