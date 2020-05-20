package com.gageshan.miaosha.mapper;

import com.gageshan.miaosha.model.MiaoshaOrder;
import com.gageshan.miaosha.model.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Create by gageshan on 2020/5/17 17:35
 */
@Mapper
public interface OrderMapper {
    MiaoshaOrder getOrderByGoodsIdUserId(long userId, long goodsId);

    void insert(OrderInfo orderInfo);

    void insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    OrderInfo getOrderById(long orderId);
}
