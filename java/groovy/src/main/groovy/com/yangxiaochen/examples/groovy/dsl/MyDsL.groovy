package com.yangxiaochen.examples.groovy.dsl

/**
 * @author yangxiaochen
 * @date 16/9/7 上午1:25
 */
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

class L {
    String name
    String desc
}

class BizObj {
    String desc
    List participants
    List attachments
    List variables
    List variableGroups

    def call(){
        println "call"
    }
}

def biz(name, @DelegatesTo(value = BizObj, strategy = Closure.DELEGATE_ONLY)Closure closure) {
    println name
    def bodySpec = new BizObj()
    def code = closure.rehydrate(bodySpec, this, this)
    code.resolveStrategy = Closure.DELEGATE_ONLY
    println code.delegate.dump()

    
    code.call()
    println code.delegate.dump()
}