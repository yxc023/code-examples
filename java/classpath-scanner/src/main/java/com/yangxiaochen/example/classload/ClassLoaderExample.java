package com.yangxiaochen.example.classload;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author yangxiaochen
 * @date 2017/4/6 23:06
 */
public class ClassLoaderExample {

    public static class MyClassLoader extends URLClassLoader {

        public MyClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        public MyClassLoader(URL[] urls) {
            super(urls);
        }

        public MyClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
            super(urls, parent, factory);
        }

    }


    /**
     *
     * 自定义classloader
     * 1. 用来运行子系统/子模块, 可以给子模块设定classpath
     * 2. 用来加载实现类
     *
     * @param args
     * @throws MalformedURLException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        List<String> paths = Arrays.asList("/Users/yangxiaochen/develop/yxc023.github.com/code-examples/java/concurrent/build/classes/test");
        URL[] urls = new URL[paths.size()];
        for (int i = 0; i < paths.size(); i++) {
            urls[i] = Paths.get(paths.get(i)).toUri().toURL();
        }


        MyClassLoader classLoader = new MyClassLoader(urls, ClassLoaderExample.class.getClassLoader());

        Class clazz = classLoader.loadClass("BlockQueueTest");
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("testBarrier");
        method.invoke(obj);
    }
}
