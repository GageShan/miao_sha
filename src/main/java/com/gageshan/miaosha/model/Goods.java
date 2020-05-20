package com.gageshan.miaosha.model;

import lombok.Data;

/**
 * Create by gageshan on 2020/5/16 18:16
 */
@Data
public class Goods {
    private long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private double goodsPrice;
    private int goodsStock;
}