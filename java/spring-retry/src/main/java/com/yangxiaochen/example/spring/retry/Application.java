package com.yangxiaochen.example.spring.retry;

import org.springframework.aop.support.AopUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * @author yangxiaochen
 * @date 2017/5/4 12:36
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableRetry
public class Application {


    public static void main(String[] args) throws Throwable {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        RetryableService retryableService = applicationContext.getBean(RetryableService.class);

        System.out.println(AopUtils.isAopProxy(retryableService));
        retryableService.service1(1);


        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));
        String result = retryTemplate.execute(new RetryCallback<String, Throwable>() {
            @Override
            public String doWithRetry(RetryContext context) throws Throwable {
                applicationContext.getBean(RetryableService2.class).service2();
                return "haha";
            }
        }, new RecoveryCallback<String>() {
            @Override
            public String recover(RetryContext context) throws Exception {
//                throw new Exception("haha");
                return "lala";
            }
        });
        System.out.println(result);
        System.in.read();


    }


}
