package me.silentdoer.demointerceptorprinciple;

import me.silentdoer.demointerceptorprinciple.support.Interceptor;
import me.silentdoer.demointerceptorprinciple.support.MethodInvocation;
import me.silentdoer.demointerceptorprinciple.support.PluginInterface;
import me.silentdoer.demointerceptorprinciple.support.interceptor.TestInterceptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoInterceptorPrincipleApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("test111111");
        TestInterceptor interceptor = new TestInterceptor();
        PluginInterface foo = (PluginInterface) interceptor.register(new Test());
        foo.execute2(3L);
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoInterceptorPrincipleApplication.class, args);
    }

    public static class Test implements PluginInterface {

        @Override
        public void execute1() {
            System.out.println("AAA");
        }

        @Override
        public void execute2(Long num) {
            System.out.println("BBB" + num);
        }
    }
}
