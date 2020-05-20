package com.gageshan.miaosha.service;

import com.gageshan.miaosha.model.MiaoshaOrder;
import com.gageshan.miaosha.model.OrderInfo;
import com.gageshan.miaosha.model.User;
import com.gageshan.miaosha.model.vo.GoodsVO;
import com.gageshan.miaosha.redis.MiaoshaKey;
import com.gageshan.miaosha.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by gageshan on 2020/5/17 17:27
 */
@Service
@Slf4j
public class MiaoshaService {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisService redisService;
    @Transactional
    public OrderInfo miaosha(User user, GoodsVO goods) {
        //减少库存
        boolean success = goodsService.reduceStock(goods);
        if(success) {
            //生成订单
            log.info(user.getId() + "---" + goods.getStockCount());
            return orderService.createOrder(user,goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }



    public long getMiaoshaResult(long userId, long goodsId) {
        MiaoshaOrder miaoshaOrder = orderService.getOrderByGoodsIdUserId(userId, goodsId);
        if(miaoshaOrder != null) {
            return miaoshaOrder.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, "" + goodsId);
    }

    private void setGoodsOver(long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }
}
