package com.gageshan.miaosha.model.vo;

import com.gageshan.miaosha.model.Goods;
import lombok.Data;

import java.util.Date;

/**
 * Create by gageshan on 2020/5/16 18:16
 */
@Data
public class GoodsVO extends Goods {
    private double miaoshaPrice;
    private int stockCount;
    private Date startDate;
    private Date endDate;


    @Override
    public String toString() {

        return "GoodsVO{" +
                super.toString() +
                "miaoshaPrice=" + miaoshaPrice +
                ", stockCount=" + stockCount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
