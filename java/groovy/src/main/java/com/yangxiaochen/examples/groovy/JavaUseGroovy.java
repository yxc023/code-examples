package com.yangxiaochen.examples.groovy;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.IOException;
import java.util.Date;

/**
 * @author yangxiaochen
 * @date 2016/12/4 21:28
 */
public class JavaUseGroovy {
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
//        CompilerConfiguration configuration = new CompilerConfiguration();
//        configuration.setScriptBaseClass("com.yangxiaochen.examples.groovy.ScriptResolver");
//        configuration.setRecompileGroovySource(false);
//        GroovyShell groovyShell = new GroovyShell(JavaUseGroovy.class.getClassLoader(), configuration);
        for (int i = 0; i < 1000000; i++) {
            CompilerConfiguration configuration = new CompilerConfiguration();
            configuration.setScriptBaseClass("com.yangxiaochen.examples.groovy.ScriptResolver");
            configuration.setRecompileGroovySource(false);
            GroovyShell groovyShell = new GroovyShell(JavaUseGroovy.class.getClassLoader(), configuration);

            Script s1 = groovyShell.parse("println list[1]");
            s1.run();
            groovyShell.setProperty("date", new Date());
            Script s2 = groovyShell.parse("print \"now is ${date}\"");
            s2.run();

//            groovyShell.getClassLoader().clearCache();

//            Field globalClassValue = ClassInfo.class.getDeclaredField("globalClassValue");
//            globalClassValue.setAccessible(true);
//            GroovyClassValue classValueBean = (GroovyClassValue) globalClassValue.get(null);
//            classValueBean.remove(s1.getClass());
//            classValueBean.remove(s2.getClass());
//            ClassInfo.remove(s1.getClass());
//            ClassInfo.remove(s2.getClass());

        }
    }
}
