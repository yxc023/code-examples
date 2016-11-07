package com.yangxiaochen.examples.jsch

import com.pastdev.jsch.DefaultSessionFactory
import com.pastdev.jsch.command.CommandRunner

/**
 * @author yangxiaochen
 * @date 16/8/25 下午5:50
 */


fun main(args: Array<String>) {

    var config = createDevConfig()

    var defaultSessionFactory = DefaultSessionFactory(
            config.sshusername, config.sshhost, 22)

    defaultSessionFactory.setPassword(config.sshpasswd)
    defaultSessionFactory.setKnownHosts("~/.ssh/known_hosts")
    defaultSessionFactory.setConfig("StrictHostKeyChecking", "no")
    var session = defaultSessionFactory.newSession()

    session.connect()
    try {
        val commandStr = "cat var/fn-gte/logs/all.log"
        val commandRunner = CommandRunner(defaultSessionFactory)
        commandRunner.execute("")
        val result = commandRunner.execute(commandStr + " | wc -l")
        if (result.exitCode != 0) {
            println(result.stderr)
            return
        }


        val lc = result.stdout.replace(Regex("\\s*"),"").toInt()

        if (lc > 1000) {
            println("result lines: ${lc}, continue? Input 'Y' to continue")
            val line = readLine()
            if (!line.equals("Y", true)) {
                return
            }
        }

        var ret = commandRunner.execute(commandStr)

        println(ret.stdout)
    } finally {
        println("finally")
        session.disconnect()
        Thread.sleep(1000)
        System.exit(0)
    }

}