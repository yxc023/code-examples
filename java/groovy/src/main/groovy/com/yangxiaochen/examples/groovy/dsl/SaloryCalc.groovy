package com.yangxiaochen.examples.groovy.dsl

/**
 * @author yangxiaochen
 * @date 2017/8/12 22:48
 */
class SaloryCalc {

    static void main(String[] args) {
        int leiji = 7000
        int leftMonth = 4

        int shebao = 5100

        int gongzi = 32100

        int nianzhong = 52000 * 1.4


        int mostSmallShui = 99999999
        int mostSmallLeft = 0
        for (int left = 0; left < 10000; left+= 100) {
            int gongzishui = leftMonth * gongzishui(gongzi - shebao - left)
            int nianzhongshui = nianzhongshui(25100 + nianzhong + leiji + left * leftMonth - shebao)
            println gongzi + nianzhong + leiji + left * leftMonth - shebao
            println gongzishui
            println nianzhongshui
            println gongzishui + nianzhongshui
            println left

            println ""

            if (gongzishui + nianzhongshui < mostSmallShui) {
                mostSmallShui = gongzishui + nianzhongshui
                mostSmallLeft = left
            }
        }
        println mostSmallLeft
        println mostSmallShui


    }





    static int gongzishui(int gongzi) {
        int nashuigongzi = gongzi - 3500
        if (nashuigongzi > 35000) {
            return nashuigongzi * 0.3 - 2755
        } else if (nashuigongzi > 9000) {
            return nashuigongzi * 0.25 - 1005
        } else if (nashuigongzi > 4500) {
            return nashuigongzi * 0.20 - 555

        }
        return 0
    }

    static int nianzhongshui(int jine) {
        if (jine > 108000) {
            return jine * 0.25 - 1005
        } else if (jine > 54000) {
            return jine * 0.20 - 555
        } else if (jine > 18000) {
            return jine * 0.10 - 105
        }

        return 0
    }
}
