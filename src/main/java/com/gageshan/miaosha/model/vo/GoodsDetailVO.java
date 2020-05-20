package com.gageshan.miaosha.model.vo;

import com.gageshan.miaosha.model.User;
import lombok.Data;

/**
 * Create by gageshan on 2020/5/18 22:45
 */
@Data
public class GoodsDetailVO {
    private int miaoshaStatus = 0;
    private int remainSeconds = 0;
    private GoodsVO goods ;
    private User user;
}
