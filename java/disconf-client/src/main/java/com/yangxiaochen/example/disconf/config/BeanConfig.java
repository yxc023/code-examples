package com.yangxiaochen.example.disconf.config;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
