import com.jcraft.jsch.Session
import com.pastdev.jsch.DefaultSessionFactory
import com.pastdev.jsch.command.CommandRunner
import com.yangxiaochen.examples.jsch.config.SshTunnelDbConfig

/**
 * @author yangxiaochen
 * @date 16/9/14 下午3:23
 */
class SSHRemote {
    public static void main(String[] args) {
        SshTunnelDbConfig config = SshTunnelDbConfig.createDevConfig();

        DefaultSessionFactory defaultSessionFactory = new DefaultSessionFactory(
                config.getSshusername(), config.getSshhost(), 22);

        defaultSessionFactory.setPassword(config.getSshpasswd());
        defaultSessionFactory.setKnownHosts("~/.ssh/known_hosts");
        defaultSessionFactory.setConfig("StrictHostKeyChecking", "no");
        Session session = defaultSessionFactory.newSession();
        session.connect();
        try {
            def commandStr = "cat var/fn-gte/logs/all.log"
            def commandRunner = new CommandRunner(defaultSessionFactory)
            commandRunner.execute("")
            def result = commandRunner.execute(commandStr + " | wc -l")
            if (result.exitCode != 0) {
                println(result.stderr)
                return
            }


            if (result.stdout.toInteger() > 1000) {
                println("result lines: ${result.stdout.trim().replaceAll( ~/\s*/,"").toInteger()}, continue?")

                def line =  new BufferedReader(new InputStreamReader(System.in)).readLine()
                if (line.trim().equalsIgnoreCase("Y")) {
                    println("YYYYY")
                }
            }

        } finally {
            sleep(1000)
            session.disconnect()
            sleep(1000)
            System.exit(0)
        }
    }
}
