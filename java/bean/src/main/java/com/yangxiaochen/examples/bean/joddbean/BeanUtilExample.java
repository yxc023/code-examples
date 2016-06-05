package com.yangxiaochen.examples.bean.joddbean;

import jodd.bean.BeanUtil;
import lombok.extern.log4j.Log4j2;

import java.util.Properties;

/**
 * @author yangxiaochen
 * @date 16/6/5 上午11:40
 */
@Log4j2
public class BeanUtilExample {

    public static void main(String[] args) {
        Properties properties = new Properties();
        BeanUtil.pojo.setProperty(properties, "[ldap.auth.enabled]", "true");
        log.info(properties);
    }
}
