package com.yangxiaochen.example.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yangxiaochen.example.spring.controller.ContextController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author yangxiaochen
 * @date 2017/5/31 17:57
 */
public class SelfApplication {

    public static void main(String[] args) {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) BeanDefinitionBuilder
                .genericBeanDefinition(ContextController.class)
                .setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE)
                .getBeanDefinition();

        factory.registerBeanDefinition("beanDefinition", beanDefinition);

        factory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof ApplicationContextAware) {
                    GenericApplicationContext context = new GenericApplicationContext(factory);
                    ((ApplicationContextAware) bean).setApplicationContext(context);
                }
                return bean;
            }
        });

        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        autowiredAnnotationBeanPostProcessor.setBeanFactory(factory);
        factory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);


        ContextController contextController = factory.getBean("beanDefinition",ContextController.class);
        try {
            contextController.index();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
