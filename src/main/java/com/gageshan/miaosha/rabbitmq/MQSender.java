package com.gageshan.miaosha.rabbitmq;

import com.gageshan.miaosha.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by gageshan on 2020/5/20 17:35
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(Object message) {
        message = RedisService.beanToString(message);
        log.info("send: " + message);
        amqpTemplate.convertAndSend(MQConfig.QUEUE,message);
    }

    public void sendMiaoshaMessage(MiaoshaMessage miaoshaMessage) {
        String message = RedisService.beanToString(miaoshaMessage);
        log.info("send: " + message);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE,message);
    }
}
