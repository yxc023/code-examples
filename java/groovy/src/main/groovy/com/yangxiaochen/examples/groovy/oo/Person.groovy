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


def foo(Object... args) { args }

Integer[] ints = [1, 2]
println foo(ints)
def ints2 = [3, 4] as Integer[]
println ints2.class
println foo(ints,ints2)