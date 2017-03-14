package com.yangxiaochen.example.jsl._12_execution;

/**
 * @author yangxiaochen
 * @date 2017/3/14 18:26
 */
public enum SingletonEnum {
    SINGLETON_ENUM;

    SingletonEnum() {
        System.out.println("SingletonEnum init");
    }
}
