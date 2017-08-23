package com.yangxiaochen.example.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Arrays;

/**
 * @author yangxiaochen
 * @date 2017/8/23 14:28
 */
public class FooBeanFacotryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println(Arrays.toString(beanFactory.getBeanNamesForType(SomeConfig.class)));
        System.out.println("~~");
    }
}
