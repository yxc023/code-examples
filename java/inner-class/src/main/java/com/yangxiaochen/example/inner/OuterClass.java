package com.yangxiaochen.example.inner;

/**
 * @author yangxiaochen
 * @date 2017/4/11 10:31
 */
public class OuterClass {

    public int l = 0;

    public void action() {
        System.out.println(l);
    }

    public class FeildClass {
        public int i = 2;
    }

    public Object createInner() {
        new OuterClass() {

        };

        return new OuterClass() {


            public int k = 1;
            public void action() {
                System.out.println(k);
            }
        };
    }

    public Object createFeildClass() {
        return new FeildClass();
    }
}
