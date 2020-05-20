package com.gageshan.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Create by gageshan on 2020/5/14 10:58
 */
@Service
public class RedisPoolFactory {

    @Autowired
    private RedisConfig redisConfig;

    /**
     * 注入JedisPool
     * @return
     */
    @Bean
    public JedisPool jedisPoolFactory() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisConfig.getPoolMaxIdle());
        config.setMaxTotal(redisConfig.getPoolMaxTotal());
        config.setMaxWaitMillis(redisConfig.getPoolMaxWait());
        JedisPool jedisPool = new JedisPool(config,redisConfig.getHost(),redisConfig.getPort(),
                redisConfig.getTimeout()*1000,redisConfig.getPassword(),redisConfig.getDatabase());
        return jedisPool;
    }
}
