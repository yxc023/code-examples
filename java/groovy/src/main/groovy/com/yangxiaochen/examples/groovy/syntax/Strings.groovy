assert 'ab' == 'a' + 'b'

println "========"
def sql = '''
select *
from table
where id = 1'''
println sql

def sql2 = '''select *
from table
where id = 1'''
println sql2


def sql3 = '''\
select *
from table
where id = 1'''
println sql3


// String interpolation

def name = 'Guillaume' // a plain string
def greeting = "Hello ${name}"

println greeting


def number = 3.14
println "PI = ${number}"

//shouldFail(MissingPropertyException) {
//    println "$number.toString()"
//}

number = 1
def eagerGString = "value == ${number}"
def lazyGString = "value == ${ -> number }"

assert eagerGString == "value == 1"
println lazyGString

number = 2
println eagerGString
println lazyGString


def sOneParamClosure = "1 + 2 == ${ w -> w << 3; w.append 'haha'}"
println sOneParamClosure



//When a method (whether implemented in Java or Groovy) expects a java.lang.String, but we pass a groovy.lang.GString instance, the toString() method of the GString is automatically and transparently called.
String takeString(String message) {
    assert message instanceof String
    return message
}

def message = "The message is ${'hello'}"
assert message instanceof GString

def result = takeString(message)
assert result instanceof String
assert result == 'The message is hello'


// GString and Strings having different hashCode values, using GString as Map keys should be avoided, especially if we try to retrieve an associated value with a String instead of a GString.
def key = "a"
def m = ["${key}": "letter ${key}"]

assert m["a"] == null