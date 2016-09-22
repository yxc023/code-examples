package com.yangxiaochen.examples.log.analysis
/**
 * @author yangxiaochen
 * @date 16/8/4 下午5:49
 */
class ThreadSpilt {

    public static void main(String[] args) {

        def fileMap = [:]
        File logFile = new File("/Users/yangxiaochen/Downloads/deadlock2.log")
        logFile.eachLine { line ->
            def terms = line.split(" ")
            if (terms.length <= 3) {
                return
            }
            if ((terms[0] + " " + terms[1]).matches(/^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\.[0-9]{3}$/)) {
                def threadName = terms[2][1..-2]
                if (!fileMap.containsKey(threadName)) {

                    fileMap["${threadName}"] = new File(logFile.getPath() + threadName)
                }

                fileMap["${threadName}"] << line << "\n"

            }
        }

    }
}
