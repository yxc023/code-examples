package com.yangxiaochen.examples.kotlin;

/**
 * @author yangxiaochen
 * @date 16/8/28 下午11:47
 */
public class ClassExamples {
}


open class Student(val name: String) {
    constructor() : this("haha")

    open fun v() {
    }

    fun nv() {
    }

}

class GoodStudent() : Student() {

    override fun v() {
        super.v()
    }


}


open class A {
    open fun f() {
        print("A")
    }

    fun a() {
        print("a")
    }
}

interface B {
    fun f() {
        print("B")
    } // interface members are 'open' by default

    fun b() {
        print("b")
    }
}

class C() : A(), B {
    // The compiler requires f() to be overridden:
    override fun f() {
        super<A>.f() // call to A.f()
        super<B>.f() // call to B.f()


    }

    companion object Foo {
        fun add(c1: C, c2: C) {

        }
    }
}


sealed class Expr {
    class Const(val number: Double) : Expr()
    class Sum(val e1: Expr, val e2: Expr) : Expr()
    object NotANumber : Expr()
}


fun main(args: Array<String>) {
    var s = Student()
    println(s.name)

    s = Student("yxc")
    println(s.name)

    C.add(C(), C())

    var a = A()

//    var e = Expr()

}