package com.yangxiaochen.example.disconf;

import com.yangxiaochen.example.disconf.config.BeanConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @author yangxiaochen
 * @date 2016/12/7 20:41
 */
@Configuration
@EnableAutoConfiguration
@ImportResource(locations = "classpath:spring-main.xml")
@Import(BeanConfig.class)
@ComponentScan(basePackages = "com.yangxiaochen.example.disconfig.configs")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
