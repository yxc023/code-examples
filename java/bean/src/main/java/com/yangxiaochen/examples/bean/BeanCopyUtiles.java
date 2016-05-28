package com.yangxiaochen.examples.bean;

import com.yangxiaochen.examples.bean.lombok.Person;
import jodd.bean.BeanCopy;

/**
 * @author yangxiaochen
 * @date 16/5/25 下午11:29
 */
public class BeanCopyUtiles {

    public static void main(String[] args) {
        Person p1 = new Person();
        p1.setId(110);

        Person p2 = new Person();
        BeanCopy.from(p1).to(p2).copy();
        System.out.println(p2);
    }
}
