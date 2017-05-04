package com.yangxiaochen.example.spring.retry;

import org.springframework.aop.support.AopUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * @author yangxiaochen
 * @date 2017/5/4 12:36
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableRetry
public class Application {


    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        RetryableService retryableService =  applicationContext.getBean(RetryableService.class);

        System.out.println(AopUtils.isAopProxy(retryableService));
        Executors.newFixedThreadPool(1).submit(()-> {
            retryableService.service1(1);
        });

        System.in.read();
    }


}
