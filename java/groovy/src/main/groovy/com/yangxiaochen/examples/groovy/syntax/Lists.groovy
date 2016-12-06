def heterogeneous = [1, "a", true]
println heterogeneous.class

heterogeneous = [1, 2, 3] as LinkedList
println heterogeneous.class

heterogeneous << [4, 5, 6]
println heterogeneous
println heterogeneous[-2]
println heterogeneous[-1][0]


def useMap(Map<String, Object> map) {
    print(map)
}



