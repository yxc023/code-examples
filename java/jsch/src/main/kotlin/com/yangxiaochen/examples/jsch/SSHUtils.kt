package com.yangxiaochen.examples.jsch

import com.pastdev.jsch.DefaultSessionFactory
import com.pastdev.jsch.command.CommandRunner

/**
 * @author yangxiaochen
 * @date 16/8/25 下午5:50
 */


fun main(args: Array<String>) {
    //        com.yangxiaochen.examples.jsch.SshTunnelDbConfig config = com.yangxiaochen.examples.jsch.SshTunnelDbConfig.com.yangxiaochen.examples.jsch.createOnlineConfig();
    var config = createDevConfig()
    var dbuser = config.dbuser
    var dbpasswd = config.dbpasswd
    var defaultSessionFactory = DefaultSessionFactory(
            config.sshusername, config.sshhost, 22)

    defaultSessionFactory.setPassword(config.sshpasswd)
    defaultSessionFactory.setKnownHosts("~/.ssh/known_hosts")
//        defaultSessionFactory.setConfig("StrictHostKeyChecking", "no");
    var session = defaultSessionFactory.newSession()

    session.connect()
    try {
//    session.setPortForwardingL(10240, config.dbhost, config.dbport)
        val commandStr = "cat var/fn-gte/logs/all.log"
        val commandRunner = CommandRunner(defaultSessionFactory)
        commandRunner.execute("")
        val result = commandRunner.execute(commandStr + " | wc -l")
        if (result.exitCode != 0) {
            println(result.stderr)
            return
        }


        if (result.stdout.toInt() > 1000) {
            println("result lines: ${result.stdout.toInt()}, continue?")
            val line = readLine()
            if (!line.equals("Y", true)) {
                return
            }
        }

        println(commandRunner.execute(commandStr))
    } finally {
        session.disconnect()
    }

    return


}