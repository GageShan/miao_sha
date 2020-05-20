package com.gageshan.miaosha.model;

import lombok.Data;

/**
 * Create by gageshan on 2020/5/16 18:20
 */
@Data
public class MiaoshaOrder {
    private long id;
    private long userId;
    private long orderId;
    private long goodsId;
}
