package com.yangxiaochen.example.inner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author yangxiaochen
 * @date 2017/4/11 10:34
 */
public class Printer {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        OuterClass outerClass = new OuterClass();

        System.out.println(outerClass.createFeildClass().getClass());
        System.out.println(outerClass.createInner().getClass());



        Class z = Class.forName("com.yangxiaochen.example.inner.OuterClass$2");
        // 内部类的构造方法第一个参数是持有它的类
        for (Constructor constructor : z.getDeclaredConstructors()) {
            System.out.println(constructor);
        }

        Object o = z.getDeclaredConstructor(OuterClass.class).newInstance(outerClass);
        OuterClass oc = (OuterClass) o;
        oc.action();
    }
}
