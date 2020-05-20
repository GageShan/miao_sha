package com.gageshan.miaosha.service;

import com.gageshan.miaosha.mapper.OrderMapper;
import com.gageshan.miaosha.model.MiaoshaOrder;
import com.gageshan.miaosha.model.OrderInfo;
import com.gageshan.miaosha.model.User;
import com.gageshan.miaosha.model.vo.GoodsVO;
import com.gageshan.miaosha.redis.OrderKey;
import com.gageshan.miaosha.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Create by gageshan on 2020/5/17 17:26
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 查询redis中的秒杀订单信息
     * @param userId
     * @param goodsId
     * @return
     */
    public MiaoshaOrder getOrderByGoodsIdUserId(long userId, long goodsId) {
//        return orderMapper.getOrderByGoodsIdUserId(userId,goodsId);
        return redisService.get(OrderKey.getOrder,userId + "_" + goodsId, MiaoshaOrder.class);
    }

    @Transactional
    public OrderInfo createOrder(User user, GoodsVO goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setStatus(0);
        orderMapper.insert(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());
        orderMapper.insertMiaoshaOrder(miaoshaOrder);

        redisService.set(OrderKey.getOrder,user.getId() + "_" + goods.getId(),miaoshaOrder);
        return orderInfo;
    }

    public OrderInfo getOrderById(long orderId) {
        return orderMapper.getOrderById(orderId);
    }
}
