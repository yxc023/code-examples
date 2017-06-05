package com.yangxiaochen.example.spring.context;

import org.springframework.util.TypeUtils;

import javax.security.auth.login.Configuration;

/**
 * @author yangxiaochen
 * @date 2017/6/5 20:18
 */
@BeanOverrideConfiguration
public class BeanOverrideMain {
    public static void main(String[] args) {
        if (TypeUtils.isAssignable(Configuration.class, BeanOverrideConfiguration.class)) {
            System.out.println("true");
        }
    }
}
