package com.gageshan.miaosha.rabbitmq;

import com.gageshan.miaosha.model.User;
import lombok.Data;

/**
 * Create by gageshan on 2020/5/20 21:52
 */
@Data
public class MiaoshaMessage {
    private User user;
    private long goodsId;
}
