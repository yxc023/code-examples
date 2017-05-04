package com.yangxiaochen.example.spring.retry;

import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * @author yangxiaochen
 * @date 2017/5/4 13:20
 */
@Component
public class RetryableService {

    public RetryableService() {
        System.out.println("init");
    }


    @Retryable(value = {Exception.class}, exceptionExpression = "#{message.contains('this can be retried')}")
    public void service1(int arg) {
        System.out.println("do service1");
        throw new RuntimeException("this can be retried");
    }

    @Recover
    public void recover(RuntimeException e) {
        // ... panic
        System.out.println("runtimeE");
    }
}
