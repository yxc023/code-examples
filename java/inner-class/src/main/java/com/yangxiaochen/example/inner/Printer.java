package com.yangxiaochen.example.inner;

/**
 * @author yangxiaochen
 * @date 2017/4/11 10:34
 */
public class Printer {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        OuterClass outerClass = new OuterClass();

//        System.out.println(outerClass.createFeildClass().getClass());
//        System.out.println(outerClass.createInner().getClass());


        Class z = Class.forName("com.yangxiaochen.example.inner.OuterClass$2");
        Object o = z.newInstance();
        OuterClass oc = (OuterClass) o;
        oc.action();
    }
}
