package com.yangxiaochen.example.spring.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangxiaochen
 * @date 2017/6/5 20:17
 */
@Configuration
public class BeanConfig {
    @Value("${value}")
    String value;

    @Bean
    public SomeConfig someConfig() {
        if (value == null) {
            throw new RuntimeException("value is null");
        }
        System.out.println(value);
        return new SomeConfig();
    }

    @Bean
    public SomeConfig2 someConfig2() {
        if (value == null) {
            throw new RuntimeException("value is null");
        }
        System.out.println(value);
        return new SomeConfig2();
    }
}
