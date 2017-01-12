package com.yangxiaochen.example.classpath;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author yangxiaochen
 * @date 2017/1/12 12:26
 */
public class ClassPathDump {

    public static void main(String[] args) throws IOException {

        System.out.println("without jre jars");
        new FastClasspathScanner().scan().getUniqueClasspathElements()
                .forEach(System.out::println);

        System.out.println();
        System.out.println("all jars");
        URL[] urls =((URLClassLoader) ClassPathDump.class.getClassLoader()).getURLs();
        System.out.println(ClassPathDump.class.getClassLoader());
        System.out.println(ClassPathDump.class.getClassLoader().getParent());
        System.out.println(ClassPathDump.class.getClassLoader().getParent().getParent());
        for (URL url : urls) {
            System.out.println(url);
        }
    }
}
