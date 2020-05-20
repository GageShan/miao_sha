package com.gageshan.miaosha.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by gageshan on 2020/5/20 17:36
 */
@Configuration
public class MQConfig {

    public static final String QUEUE = "queue";

    public static final String MIAOSHA_QUEUE = "miaosha.queue";
//    @Bean
//    public Queue miaosha_queue() {
//        return new Queue(MIAOSHA_QUEUE,true);
//    }

    @Bean
    public Queue queue() {
        return new Queue(MIAOSHA_QUEUE,true);
    }
}
