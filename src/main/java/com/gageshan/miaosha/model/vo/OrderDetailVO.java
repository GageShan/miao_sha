package com.gageshan.miaosha.model.vo;

import com.gageshan.miaosha.model.OrderInfo;
import lombok.Data;

/**
 * Create by gageshan on 2020/5/19 16:20
 */
@Data
public class OrderDetailVO {
    private OrderInfo orderInfo;
    private GoodsVO goods;
}
