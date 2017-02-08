package com.yangxiaochen.example.disconfig.configs

import com.baidu.disconf.client.common.annotations.DisconfFile
import com.baidu.disconf.client.common.annotations.DisconfFileItem
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
/**
 * @author yangxiaochen
 * @date 2017/2/6 17:40
 */
@Service
@Scope("singleton")
@DisconfFile(filename = "redis.properties")
class JedisConfig {

//    @Value('${redis.host}')
    String host
//    @Value('${redis.port}')
    Integer port

    @DisconfFileItem(name = "redis.host", associateField = "host")
    String getHost() {
        return host
    }

    @DisconfFileItem(name = "redis.port", associateField = "port")
    Integer getPort() {
        return port
    }
}
