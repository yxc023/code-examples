package com.yangxiaochen.examples.bean.lombok;

import lombok.Data;
import lombok.ToString;

/**
 * @author yangxiaochen
 * @date 16/5/29 下午10:29
 */
@ToString
@Data
public class Dog {
    private int id;
    private String name;
    private String ownerName;
}
