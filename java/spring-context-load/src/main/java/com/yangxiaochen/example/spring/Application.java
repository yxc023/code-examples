package com.yangxiaochen.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author yangxiaochen
 * @date 2017/3/6 23:37
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class Application {
    public static void main(String[] args) {



        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    }
}
