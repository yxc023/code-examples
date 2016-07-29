package com.yangxiaochen.examples.groovy.oo;

/**
 * @author yangxiaochen
 * @date 16/7/29 上午12:59
 */
trait FlyingAbility {
    abstract String name()

    String fly() { "I'm flying!" }
}


class Bird implements FlyingAbility {
    String name() {
        return "lala"
    }
}

def b = new Bird()
println b.fly()



trait Extra {
    String extra() { "I'm an extra method" }
}
class Something {
    String doSomething() { 'Something' }
}

def s = new Something() as Extra

println s.extra()




trait A { void methodFromA() {println "a"} }
trait B { void methodFromB() {println "b"} }

class C {}

def c = new C()
//c.methodFromA()
//c.methodFromB()
def d = c.withTraits A, B
d.methodFromA()
d.methodFromB()