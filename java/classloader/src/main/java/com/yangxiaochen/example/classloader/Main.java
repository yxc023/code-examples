package com.yangxiaochen.example.classloader;

import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;

/**
 * @author yangxiaochen
 * @date 2017/12/26 00:20
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        URLClassLoader appClassLoader = (URLClassLoader) Main.class.getClassLoader();
        YxcClassLoader yxcClassLoader = new YxcClassLoader(appClassLoader.getURLs(),appClassLoader);

        Class c = yxcClassLoader.loadClass("com.yangxiaochen.example.classloader.myclass.Hello");
        Object object = c.newInstance();
        System.out.println(c.getDeclaredMethod("getFoo").invoke(object).getClass());
        System.out.println(c.getDeclaredMethod("getFoo").invoke(object).getClass().getClassLoader());
    }
}
