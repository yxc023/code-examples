package com.yangxiaochen.example.spring.retry;

import org.springframework.stereotype.Component;

/**
 * @author yangxiaochen
 * @date 2017/5/9 14:49
 */
@Component
public class RetryableService2 {

    public void service2() {
        System.out.println("do service2");
        throw new RuntimeException("this can be retried");
    }
}
