package com.yangxiaochen.example.spring;

import com.yangxiaochen.example.spring.context.SomeConfig2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yangxiaochen
 * @date 2017/8/28 00:27
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class SpringCommonTest {

    @MockBean
    public SomeConfig2 someConfig2;

    @Test
    public void test() throws Exception {
        Mockito.when(someConfig2.foo()).thenReturn("hahahahahhaha~");
        System.out.println(someConfig2.foo());
    }
}
