package com.yangxiaochen.example.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yangxiaochen.example.other.SomeOtherBeanUseConfigClass;
import com.yangxiaochen.example.spring.context.FooBeanFacotryPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * @author yangxiaochen
 * @date 2017/3/6 23:37
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@ImportResource("classpath:spring.xml")
public class Application {
    public static void main(String[] args) throws JsonProcessingException {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .initializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
                    @Override
                    public void initialize(ConfigurableApplicationContext applicationContext) {
                        applicationContext.getBeanFactory().registerSingleton("testBeanFacotryProcessor", new FooBeanFacotryPostProcessor());
                    }
                }).contextClass(AnnotationConfigApplicationContext.class)
                .build()
                .run(args);
        BeanDefinition beanDefinition = context.getBeanFactory().getBeanDefinition("contextController");

        System.out.println(beanDefinition);
        System.out.println(context.getBean("contextController"));


        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) context);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring-beans.xml");

        System.out.println(context.getBean("contextController"));

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner((BeanDefinitionRegistry) context);
        scanner.scan("com.yangxiaochen.example.other");
//        context.refresh();
        System.out.println(context.getBean("someOtherBean"));

        System.out.println(context.getBean(SomeOtherBeanUseConfigClass.class));

    }
}
