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

    @PostConstruct
    public void start() {
        logger.info("{}:{}", jedisConfig.getHost(), jedisConfig.getPort());
    }
}
