package com.gageshan.miaosha.rabbitmq;

import com.gageshan.miaosha.model.MiaoshaOrder;
import com.gageshan.miaosha.model.User;
import com.gageshan.miaosha.model.vo.GoodsVO;
import com.gageshan.miaosha.redis.RedisService;
import com.gageshan.miaosha.service.GoodsService;
import com.gageshan.miaosha.service.MiaoshaService;
import com.gageshan.miaosha.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by gageshan on 2020/5/20 17:36
 */
@Service
@Slf4j
public class MQReceiver {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        log.info("receive:" + message);
        MiaoshaMessage miaoshaMessage = RedisService.stringToBean(message, MiaoshaMessage.class);
        User user = miaoshaMessage.getUser();
        long goodsId = miaoshaMessage.getGoodsId();

        GoodsVO goods = goodsService.getGoodsVOById(goodsId);
        int stockCount = goods.getStockCount();

        if(stockCount <= 0) {
            return ;
        }
        MiaoshaOrder miaoshaOrder = orderService.getOrderByGoodsIdUserId(user.getId(),goodsId);
        if(miaoshaOrder != null) {
            return;
        }
        miaoshaService.miaosha(user,goods);
    }
//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String message) {
//        log.info("receive:" + message);
//    }
}
