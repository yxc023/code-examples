import com.jcraft.jsch.Session
import com.pastdev.jsch.DefaultSessionFactory
import com.yangxiaochen.examples.jsch.config.SshTunnelDbConfig

/**
 * @author yangxiaochen
 * @date 16/9/14 下午3:23
 */
class SSHRemote {
    public static void main(String[] args) {
        //        com.yangxiaochen.examples.jsch.SshTunnelDbConfig config = com.yangxiaochen.examples.jsch.SshTunnelDbConfig.com.yangxiaochen.examples.jsch.createOnlineConfig();
        SshTunnelDbConfig config = SshTunnelDbConfig.createDevConfig();

        DefaultSessionFactory defaultSessionFactory = new DefaultSessionFactory(
                config.getSshusername(), config.getSshhost(), 22);

        defaultSessionFactory.setPassword(config.getSshpasswd());
        defaultSessionFactory.setKnownHosts("~/.ssh/known_hosts");
//        defaultSessionFactory.setConfig("StrictHostKeyChecking", "no");
        Session session = defaultSessionFactory.newSession();
        session.connect();
        try {
            def commandStr = "cat var/fn-gte/logs/all.log"
            def commandRunner = CommandRunner(defaultSessionFactory)
            commandRunner.execute("")
            def result = commandRunner.execute(commandStr + " | wc -l")
            if (result.exitCode != 0) {
                println(result.stderr)
                return
            }


            if (result.stdout.toInt() > 1000) {
                println("result lines: ${result.stdout.toInt()}, continue?")

                if (!line.equals("Y", true)) {
                    return
                }
            }

        }finally {
            session.disconnect()
        }
    }
}
