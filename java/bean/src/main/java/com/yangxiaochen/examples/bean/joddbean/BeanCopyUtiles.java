package com.yangxiaochen.examples.bean.joddbean;

import com.yangxiaochen.examples.bean.lombok.Dog;
import com.yangxiaochen.examples.bean.lombok.Person;
import jodd.bean.BeanCopy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangxiaochen
 * @date 16/5/25 下午11:29
 */
public class BeanCopyUtiles {

    public static void main(String[] args) {
        Person p1 = new Person();
        p1.setId(110);
        p1.setName("John");
        p1.setTel("1101834110");

        // to same type
        Person p2 = new Person();
        BeanCopy.from(p1).to(p2).copy();
        System.out.println(p2);

        // to diffrent type
        Dog d1 = new Dog();
        BeanCopy.from(p1).to(d1).copy();
        System.out.println(d1);

        // to map
        Map<String,Object> map = new HashMap();
        BeanCopy.from(p1).to(map).copy();
        System.out.println(map);

        // from map
        Person p3 = new Person();
        BeanCopy.from(map).to(p3).ignoreNulls(true).copy();
        System.out.println(p3);



    }
}
