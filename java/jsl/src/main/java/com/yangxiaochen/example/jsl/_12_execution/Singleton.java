package com.yangxiaochen.example.jsl._12_execution;

/**
 * @author yangxiaochen
 * @date 2017/3/14 18:13
 */
public class Singleton {
//    private static Singleton singleton = new Singleton();

    private Singleton() {
        System.out.println("singleton init");
    }

    public static Singleton getInstance() {
        return Holder.singleton;
    }

    private static class Holder {
        public static Singleton singleton = new Singleton();

    }
}
