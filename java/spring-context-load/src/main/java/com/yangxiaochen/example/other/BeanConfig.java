package com.yangxiaochen.example.other;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangxiaochen
 * @date 2017/8/27 21:57
 */
@Configuration("beanconfig2")
public class BeanConfig {

    @Bean
    public SomeOtherBeanUseConfigClass someOtherBeanUseConfigClass() {
        return new SomeOtherBeanUseConfigClass();
    }
}
