package com.yangxiaochen.example.spring.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangxiaochen
 * @date 2017/8/23 14:10
 */
@Component
public class SomeConfig2 {
    @Autowired
    public SomeConfig someConfig;

    public String foo() {
        return "fooo";
    }
}
