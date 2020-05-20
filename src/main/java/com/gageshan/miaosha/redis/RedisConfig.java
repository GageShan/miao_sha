package com.gageshan.miaosha.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Create by gageshan on 2020/5/13 23:47
 */
@Data
@Component
public class RedisConfig {
    @Value("${redis.host}")
    private String host;
    @Value("${redis.database}")
    private int database;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.timeout}")
    private int timeout;
    @Value("${redis.poolMaxTotal}")
    private int poolMaxTotal;
    @Value("${redis.poolMaxIdle}")
    private int poolMaxIdle;
    @Value("${redis.poolMaxWait}")
    private int poolMaxWait;
}
