class Car {
    String make
    String model
}

def cars = [
        new Car(make: 'Peugeot', model: '508'),
        new Car(make: 'Renault', model: 'Clio')]
def makes = cars*.make


println cars*.make


int function(int x, int y, int z) {
    x * y + z
}

def args = [4, 5, 6]
println function(*args)

def items = [4, 5]
def list = [1, 2, 3, *items, 6]
assert list == [1, 2, 3, 4, 5, 6]


def m1 = [c: 3, d: 4]
def map = [a: 1, b: 2, *: m1, d: 8]
assert map == [a: 1, b: 2, c: 3, d: 8]