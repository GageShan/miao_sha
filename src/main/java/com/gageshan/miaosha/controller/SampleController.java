package com.gageshan.miaosha.controller;

import com.gageshan.miaosha.rabbitmq.MQSender;
import com.gageshan.miaosha.redis.RedisConfig;
import com.gageshan.miaosha.redis.RedisPoolFactory;
import com.gageshan.miaosha.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Create by gageshan on 2020/5/14 10:54
 */
@Controller
public class SampleController {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private MQSender mqSender;


    @GetMapping("/")
    @ResponseBody
    public String hello() {
        Jedis jedis = jedisPool.getResource();
        jedis.set("name","gageshan");
        return jedis.get("name");
    }

    @GetMapping("/mq")
    @ResponseBody
    public Result<String> rabbitmq() {
        mqSender.send("hello, world");

        return Result.success("hello world");
    }
}
