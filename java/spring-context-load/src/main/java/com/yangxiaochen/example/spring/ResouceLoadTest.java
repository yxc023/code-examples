package com.yangxiaochen.example.spring;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author yangxiaochen
 * @date 2017/2/10 16:34
 */
public class ResouceLoadTest {

    public static void main(String[] args) throws IOException {
        Enumeration<URL> urls = ClassLoader.getSystemResources("");
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            System.out.println(url);
        }
    }
}
