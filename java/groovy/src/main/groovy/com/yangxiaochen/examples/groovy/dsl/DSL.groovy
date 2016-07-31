show = { println it }
square_root = { Math.sqrt(it) }

def please(action) {
    [the: { what ->
        [of: { n -> action(what(n)) }]
    }
    ]
}

// equivalent to: please(show).the(square_root).of(100)
please show the square_root of 100


def split(String string) {
    [on: { sp ->
        [trimming: { trim ->
           string.split sp collect{ it.trim().replaceAll(trim, "") }
        }]
    }]
}

//def split(String string) {
//    def on = { sp->
//        [trimming: { trim ->
//            string.split sp collect{ it.trim().replaceAll(trim, "") }
//        }]
//    }
//
//}


def result = split "_a ,_b_ ,c__" on ',' trimming '_'
println result