package com.yangxiaochen.example.disconfig.configs

import com.baidu.disconf.client.common.annotations.DisconfFileItem
import org.springframework.context.annotation.Scope
/**
 * @author yangxiaochen
 * @date 2017/2/6 17:40
 */
//@Service
@Scope("singleton")
//@DisconfFile(filename = "redis.properties")
//@DisconfUpdateService(classes = JedisConfig.class)
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
