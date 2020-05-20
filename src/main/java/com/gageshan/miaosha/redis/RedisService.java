package com.gageshan.miaosha.redis;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by gageshan on 2020/5/14 11:26
 */
@Service
public class RedisService {
    @Autowired
    private JedisPool jedisPool;

    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }
    public <T> boolean set(KeyPrefix prefix, String key, T value) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String json = beanToString(value);
            int seconds = prefix.getExpireSeconds();
            if(seconds <= 0) {
                jedis.set(realKey,json);
            } else {
                jedis.setex(realKey,seconds,json);
            }
        } finally {
            returnToPool(jedis);
        }
        return true;
    }

    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.del(realKey) > 0;
        } finally {
            returnToPool(jedis);
        }
    }


    public <T> long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }
    public static  <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if(clazz == String.class) {
            return (String) value;
        } else if(clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    public static  <T> T stringToBean(String str, Class<T> clazz) {

        if(StringUtils.isBlank(str) || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if(clazz == String.class) {
            return (T) str;
        } else if(clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return (T) JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }
}
