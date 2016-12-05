package com.yangxiaochen.examples.groovy;

import groovy.lang.GroovyShell;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author yangxiaochen
 * @date 2016/12/4 21:28
 */
public class JavaUseGroovy {
    public static void main(String[] args) throws IOException {

        GroovyShell groovyShell = new GroovyShell();
        groovyShell.evaluate(new File("groovy/src/main/resources/Lists.groovy"));
        groovyShell.evaluate("4 * 12");

        groovyShell.setProperty("date", new Date());
        groovyShell.evaluate("now is ${date}");

    }
}
