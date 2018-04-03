package com.yangxiaochen.example.classloader;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * @author yangxiaochen
 * @date 2017/12/25 23:50
 */
public class YxcClassLoader extends URLClassLoader {
    public YxcClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public YxcClassLoader(URL[] urls) {
        super(urls);
    }

    public YxcClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    @Override
    public final Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        Class<?> c = null;
        if (name.startsWith("com.yangxiaochen")) {
            synchronized (getClassLoadingLock(name)) {
                c = findLoadedClass(name);
                if (c == null) {
                    long t0 = System.nanoTime();
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t0);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
        } else {
            c = getParent().loadClass(name);
        }
        if (resolve) {
            resolveClass(c);
        }
        return c;
    }


}
