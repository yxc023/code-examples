import org.springframework.core.io.ClassPathResource
import java.util.*

/**
 * @author yangxiaochen
 * @date 16/6/28 下午9:06
 */
data class SshTunnelDbConfig(val sshhost: String,
                             val sshusername: String,
                             val sshpasswd: String,
                             val dbhost: String,
                             val dbuser: String,
                             val dbpasswd: String,
                             val dbport: Int)

fun createOnlineConfig(): SshTunnelDbConfig {
    val resource = ClassPathResource("params.properties");
    val properties = Properties();
    properties.load(resource.getInputStream());

    val config = SshTunnelDbConfig(
            properties.getProperty("online.ssh.host"),
            properties.getProperty("online.ssh.username"),
            properties.getProperty("online.ssh.passwd"),
            properties.getProperty("online.db.host"),
            properties.getProperty("online.db.user"),
            properties.getProperty("online.db.passwd"),
            Integer.valueOf(Optional.ofNullable(properties.getProperty("online.db.port")).orElse("3306"))
    )
    return config
}

fun createDevConfig(): SshTunnelDbConfig {
    val resource = ClassPathResource("params.properties");
    val properties = Properties();
    properties.load(resource.getInputStream());

    val config = SshTunnelDbConfig(
            properties.getProperty("dev.ssh.host"),
            properties.getProperty("dev.ssh.username"),
            properties.getProperty("dev.ssh.passwd"),
            properties.getProperty("dev.db.host"),
            properties.getProperty("dev.db.user"),
            properties.getProperty("dev.db.passwd"),
            Integer.valueOf(Optional.ofNullable(properties.getProperty("dev.db.port")).orElse("3306"))
    )
    return config
}

