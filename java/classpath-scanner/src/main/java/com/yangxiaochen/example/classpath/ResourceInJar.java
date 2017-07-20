package com.yangxiaochen.example.classpath;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author yangxiaochen
 * @date 2017/7/20 13:01
 */
public class ResourceInJar {
    public static void main(String[] args) throws IOException {
        URLClassLoader classLoader = (URLClassLoader) ResourceInJar.class.getClassLoader();
        for (URL url : classLoader.getURLs()) {
            System.out.println(url);
        }
        System.out.println();
        URL url = ResourceInJar.class.getClassLoader().getResource("json");
        System.out.println(url);
        JarURLConnection conn = (JarURLConnection) url.openConnection();
        System.out.println(conn.getJarEntry().getAttributes());
        System.out.println(conn.getJarEntry().isDirectory());
        JarFile jarFile = conn.getJarFile();
        System.out.println(jarFile);
        Enumeration<JarEntry> elems = jarFile.entries();
        while (elems.hasMoreElements()) {
            System.out.println(elems.nextElement());
        }

    }
}
