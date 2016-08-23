package com.yangxiaochen.examples.kotlin

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
        print("No argument: ${args.size}")

        return
    }

    print("First argument: ${args[0]}")
}


fun parseInt(str: String): Int? {
    return null
}

