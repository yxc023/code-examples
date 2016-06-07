package com.yangxiaochen.examples.bean.joddbean;

import com.yangxiaochen.examples.bean.lombok.Dog;
import com.yangxiaochen.examples.bean.lombok.Person;
import jodd.bean.BeanUtil;
import lombok.extern.log4j.Log4j2;

import java.util.Properties;

/**
 * @author yangxiaochen
 * @date 16/6/5 上午11:40
 */
@Log4j2
public class BeanUtilExample {

    public static void main(String[] args) {
        Properties properties = new Properties();
        BeanUtil.pojo.setProperty(properties, "[ldap.auth.enabled]", "true");
        log.info(properties);


        Person p1 = new Person();
        p1.setId(110);
        p1.setName("John");
        p1.setTel("1101834110");

        Dog dog = new Dog();
        dog.setName("DDD");
        p1.setDog(dog);

        boolean has = BeanUtil.declared.hasProperty(p1, "tel");
        log.info(has);

        String dogName = BeanUtil.declaredSilent.getProperty(p1, "dog.name");
        log.info(dogName);

        BeanUtil.declaredForced.setProperty(p1, "dog.name", "VVV");
        dogName = BeanUtil.declaredSilent.getProperty(p1, "dog.name");
        log.info(dogName);

        BeanUtil.forcedSilent.setProperty(p1, "dog.name2", "VVV");
        dogName = BeanUtil.forcedSilent.getProperty(p1, "dog.name2");
        log.info(dogName);
    }
}
