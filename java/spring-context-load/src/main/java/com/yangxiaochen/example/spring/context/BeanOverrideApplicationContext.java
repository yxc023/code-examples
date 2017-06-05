package com.yangxiaochen.example.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author yangxiaochen
 * @date 2017/6/5 20:08
 */
public class BeanOverrideApplicationContext extends GenericApplicationContext {

    @Override
    protected void onRefresh() throws BeansException {
        super.onRefresh();


        for (BeanFactoryPostProcessor beanFactoryPostProcessor : this.getBeanFactoryPostProcessors()) {
            if (beanFactoryPostProcessor instanceof ConfigurationClassPostProcessor) {
                ((ConfigurationClassPostProcessor) beanFactoryPostProcessor).postProcessBeanDefinitionRegistry(this);
            }
        }
    }
}
