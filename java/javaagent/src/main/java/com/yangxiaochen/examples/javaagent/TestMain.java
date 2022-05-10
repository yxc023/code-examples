package com.yangxiaochen.examples.javaagent;

public class TestMain {
    public static void main(String[] args) {
        System.out.println("i am main");
        System.out.println(new TestMain().hello());
    }


    public String hello() {
        return "hello";
    }
}
