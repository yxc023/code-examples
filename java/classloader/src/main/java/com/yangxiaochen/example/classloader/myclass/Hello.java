package com.yangxiaochen.example.classloader.myclass;

/**
 * @author yangxiaochen
 * @date 2017/12/26 00:20
 */
public class Hello {
    private Integer integer;
    private Foo foo = new Foo();


    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public Foo getFoo() {
        return foo;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
    }
}
