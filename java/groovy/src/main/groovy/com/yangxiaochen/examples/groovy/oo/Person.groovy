package com.yangxiaochen.examples.groovy.oo

/**
 * @author yangxiaochen
 * @date 16/7/26 上午12:47
 */
class Personp {

    String name
    Integer age

    def increaseAge(Integer years) {
        this.age += years
    }
}


def p = new Personp()

println p.@name