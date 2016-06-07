package com.yangxiaochen.examples.bean.lombok;

import lombok.Data;
import lombok.ToString;

/**
 * @author yangxiaochen
 * @date 16/5/26 上午12:19
 */
@ToString
public @Data class Person {
    private int id;
    private String name;
    private String tel;
    private Dog dog;
}
