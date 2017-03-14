package com.yangxiaochen.example.jsl._12_execution;

import com.yangxiaochen.examples.json.JsonUtils;

/**
 * @author yangxiaochen
 * @date 2017/3/14 18:12
 */
public class StaticInitializeTest {

    public static void main(String[] args) {
        System.out.println(Singleton.class);
        System.out.println("start");
        Singleton singleton = Singleton.getInstance();

        String s = JsonUtils.object2String(singleton);
        JsonUtils.string2Object(s, Singleton.class);


        SingletonEnum singletonEnum = SingletonEnum.SINGLETON_ENUM;
        String s2 = JsonUtils.object2String(singletonEnum);
        System.out.println("decode");
        JsonUtils.string2Object(s2, SingletonEnum.class);
    }
}
