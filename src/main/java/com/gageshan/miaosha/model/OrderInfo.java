package com.gageshan.miaosha.model;

import lombok.Data;

import java.util.Date;

/**
 * Create by gageshan on 2020/5/16 18:22
 */
@Data
public class OrderInfo {
    private long id;
    private long userId;
    private long goodsId;
    private long deliveryAddrId;
    private String goodsName;
    private int goodsCount;
    private double goodsPrice;
    private int orderChannel;
    private int status;
    private Date createDate;
    private Date payDate;
}
