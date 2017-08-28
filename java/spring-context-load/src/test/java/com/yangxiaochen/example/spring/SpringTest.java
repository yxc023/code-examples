package com.yangxiaochen.example.spring;

import com.yangxiaochen.example.spring.context.SomeConfig;
import com.yangxiaochen.example.spring.context.SomeConfig2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yangxiaochen
 * @date 2017/8/23 14:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class SpringTest {
    @MockBean
    SomeConfig someConfig;
    @Autowired
    SomeConfig2 someConfig2;
    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void beanTest() throws Exception {
        Mockito.when(someConfig.foo()).thenReturn("hahahahahhaha");
        System.out.println(someConfig.foo());
//        System.out.println(someConfig2.foo());
        System.out.println(someConfig2.someConfig.foo());
    }
}
