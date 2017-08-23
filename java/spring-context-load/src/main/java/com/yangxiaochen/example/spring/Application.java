package com.yangxiaochen.example.spring;

import com.yangxiaochen.example.spring.context.FooBeanFacotryPostProcessor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
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
        new SpringApplicationBuilder(Application.class)
                .initializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
                    @Override
                    public void initialize(ConfigurableApplicationContext applicationContext) {
                        applicationContext.getBeanFactory().registerSingleton("testBeanFacotryProcessor", new FooBeanFacotryPostProcessor());
                    }
                }).build().run(args);
//        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    }
}
