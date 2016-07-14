package com.yangxiaochen.examples.jsch.config;

import lombok.Data;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * @author yangxiaochen
 * @date 16/6/28 下午9:06
 */
@Data
public class SshTunnelDbConfig {

    private String sshhost;
    private String sshusername;
    private String sshpasswd;
    private String dbhost;
    private String dbuser;
    private String dbpasswd;
    private int dbport;
    
    public static SshTunnelDbConfig createOnlineConfig() throws IOException {
        ClassPathResource resource = new ClassPathResource("params.properties");
        Properties properties = new Properties();
        properties.load(resource.getInputStream());

        SshTunnelDbConfig config = new SshTunnelDbConfig();
        config.setSshhost(properties.getProperty("online.ssh.host"));
        config.setSshusername(properties.getProperty("online.ssh.username"));
        config.setSshpasswd(properties.getProperty("online.ssh.passwd"));
        config.setDbhost(properties.getProperty("online.db.host"));
        config.setDbuser(properties.getProperty("online.db.user"));
        config.setDbpasswd(properties.getProperty("online.db.passwd"));
        config.setDbport(Integer.valueOf(Optional.ofNullable(properties.getProperty("online.db.port")).orElse("3306")));
        return config;
    }

    public static SshTunnelDbConfig createDevConfig() throws IOException {
        ClassPathResource resource = new ClassPathResource("params.properties");
        Properties properties = new Properties();
        properties.load(resource.getInputStream());

        SshTunnelDbConfig config = new SshTunnelDbConfig();
        config.setSshhost(properties.getProperty("dev.ssh.host"));
        config.setSshusername(properties.getProperty("dev.ssh.username"));
        config.setSshpasswd(properties.getProperty("dev.ssh.passwd"));
        config.setDbhost(properties.getProperty("dev.db.host"));
        config.setDbuser(properties.getProperty("dev.db.user"));
        config.setDbpasswd(properties.getProperty("dev.db.passwd"));
        config.setDbport(Integer.valueOf(Optional.ofNullable(properties.getProperty("dev.db.port")).orElse("3306")));
        return config;
    }


    public static SshTunnelDbConfig createCiConfig() throws IOException {
        ClassPathResource resource = new ClassPathResource("params.properties");
        Properties properties = new Properties();
        properties.load(resource.getInputStream());

        SshTunnelDbConfig config = new SshTunnelDbConfig();
        config.setSshhost(properties.getProperty("ci.ssh.host"));
        config.setSshusername(properties.getProperty("ci.ssh.username"));
        config.setSshpasswd(properties.getProperty("ci.ssh.passwd"));
        config.setDbhost(properties.getProperty("ci.db.host"));
        config.setDbuser(properties.getProperty("ci.db.user"));
        config.setDbpasswd(properties.getProperty("ci.db.passwd"));
        config.setDbport(Integer.valueOf(Optional.ofNullable(properties.getProperty("ci.db.port")).orElse("3306")));
        return config;
    }

}
