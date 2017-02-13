package com.yangxiaochen.example.spring;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author yangxiaochen
 * @date 2017/2/10 16:34
 */
public class ResouceLoadTest {

    @Test
    public void test() throws IOException {
        Enumeration<URL> urls = ClassLoader.getSystemResources("META-INF/spring.factories");
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            System.out.println(url);
            BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
            int c;
            while ((c = inputStream.read()) != -1) {
                System.out.print(((char) c));
            }
            System.out.println();
        }
    }
}
