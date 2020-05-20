package com.gageshan.miaosha.model;

import lombok.Data;

import java.util.Date;

/**
 * Create by gageshan on 2020/5/16 18:19
 */
@Data
public class MiaoshaGoods {
    private long id;
    private long goodsId;
    private int stockCount;
    private double miaoshaPrice;
    private Date startDate;
    private Date endDate;
}
