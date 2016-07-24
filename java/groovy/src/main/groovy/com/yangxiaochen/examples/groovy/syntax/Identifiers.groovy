def foo = 1;
println foo

def map = [:]
map.'name' = 2
println map
println map.'name'


def firstName = "Yang"
map."$firstName Xiaochen" = firstName
println map."Yang Xiaochen"