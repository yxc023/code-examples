package com.yangxiaochen.example.disconf.config;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBean;
import com.baidu.disconf.client.addons.properties.ReloadingPropertyPlaceholderConfigurer;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * @author yangxiaochen
 * @date 2017/2/7 12:02
 */
@Configuration
public class BeanConfig {

    @Bean(destroyMethod = "destroy")
    public DisconfMgrBean disconfMgrBean() {
        DisconfMgrBean bean = new DisconfMgrBean();
        bean.setScanPackage("com.yangxiaochen.example.disconfig.configs");
        return bean;
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public DisconfMgrBeanSecond disconfMgrBeanSecond() {
        return new DisconfMgrBeanSecond();
    }


    /**
     *
     * @return
     */
    @Bean
    ReloadablePropertiesFactoryBean reloadablePropertiesFactoryBean() {
        ReloadablePropertiesFactoryBean bean = new ReloadablePropertiesFactoryBean();
        bean.setLocations(Lists.newArrayList("classpath:/redis.properties"));
        return bean;
    }

    @Bean
    ReloadingPropertyPlaceholderConfigurer reloadingPropertyPlaceholderConfigurer(ReloadablePropertiesFactoryBean reloadablePropertiesFactoryBean) throws IOException {
        ReloadingPropertyPlaceholderConfigurer configurer = new ReloadingPropertyPlaceholderConfigurer();
        configurer.setIgnoreResourceNotFound(true);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.setPropertiesArray(new Properties[]{reloadablePropertiesFactoryBean.getObject()});
        return configurer;
    }
}
