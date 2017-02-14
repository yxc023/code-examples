package com.yangxiaochen.example.disconfig.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * @author yangxiaochen
 * @date 2017/2/14 12:03
 */
@Service
class JedisConfigWithoutInject {

    @Value('${redis.host}')
    String host
    @Value('${redis.port}')
    Integer port
}
