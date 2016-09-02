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
            string.split sp collect { it.trim().replaceAll(trim, "") }
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





biz("天津面签") {
    desc = "天津面签"
    participants = [
            {
                name = "JIAO_YI_ZHU_BAN"
                desc = "交易主办"
            },
            {
                name = "ZHU_BAN_ZHU_LI"
                desc = "主办助理"
                fenDan = true
            }]
    attachments = []
    variables = [
            {
                name = "MIAN_QIAN_YIN_HANG_bankName"
                desc = "面签银行总行"
                dataType = "String"
            }]
    variableGroups = []
}

def biz(name) {

    
}