package com.yangxiaochen.examples.kotlin

import java.io.Serializable

/**
 * @author yangxiaochen
 * @date 16/8/23 上午12:53
 */
fun add(a: Int, b: Int): Int {
    return a + b
}

fun sum(a: Int, b: Int) = a + b


val a: Int = 1


fun main(args: Array<String>) {
    if (args.size == 0) {
        println("No argument: ${args.size}")
        println(c)
        println(c == d)



        return
    }

    print("First argument: ${args[0]}")

}


fun parseInt(str: String): Int? {
    return null
}

data class Customer(val name: String, val email: String)

val c = Customer(name = "yxc",email = "@qq.com")
val d = Customer(name = "yxc",email = "@qq.com")


interface Foo<out T : Any> : Serializable {
    fun foo(a: Int): T
}



