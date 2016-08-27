package com.yangxiaochen.examples.jsch

import com.pastdev.jsch.DefaultSessionFactory

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
            config.sshusername, config.sshhost, 22);

    defaultSessionFactory.setPassword(config.sshpasswd)
    defaultSessionFactory.setKnownHosts("~/.ssh/known_hosts")
//        defaultSessionFactory.setConfig("StrictHostKeyChecking", "no");
    var session = defaultSessionFactory.newSession()

    session.connect()
    session.setPortForwardingL(10240, config.dbhost, config.dbport)

    session.disconnect()

}