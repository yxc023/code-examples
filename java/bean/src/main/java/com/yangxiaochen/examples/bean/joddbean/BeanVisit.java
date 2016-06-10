package com.yangxiaochen.examples.bean.joddbean;

import com.yangxiaochen.examples.bean.lombok.Person;
import jodd.bean.BeanCopy;
import jodd.bean.BeanWalker;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangxiaochen
 * @date 16/6/4 下午11:53
 */
@Log4j2
public class BeanVisit {

    public static void main(String[] args) {

        Person p1 = new Person();
        p1.setId(110);
        p1.setName("John");
        p1.setTel("1101834110");

        

        Map<String,Object> map = new HashMap();
        BeanCopy.from(p1).to(map).copy();
        System.out.println(map);

        BeanWalker walker = BeanWalker.walk((name, value) -> {
            log.info(name + " " + value);
            log.info(value.getClass());
        });
//        walker.map(map);
        walker.bean(p1);
    }
}
