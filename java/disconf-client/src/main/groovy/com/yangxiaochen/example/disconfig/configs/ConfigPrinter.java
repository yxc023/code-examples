package com.yangxiaochen.example.disconfig.configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author yangxiaochen
 * @date 2017/2/6 18:04
 */
@Service
public class ConfigPrinter {

    Logger logger = LogManager.getLogger();
    @Autowired
    JedisConfig jedisConfig;
    @Autowired
    JedisConfigWithoutInject jedisConfigWithoutInject;
//    @Autowired
//    JedisConfigWithoutInject configWithoutInject;

    @PostConstruct
    public void start() {
        new Thread(()->{
            while (true) {
                logger.info("jedisConfig {}:{}", jedisConfig.getHost(), jedisConfig.getPort());
                logger.info("jedisConfigWithoutInject: {}:{}", jedisConfigWithoutInject.getHost(), jedisConfigWithoutInject.getPort());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
