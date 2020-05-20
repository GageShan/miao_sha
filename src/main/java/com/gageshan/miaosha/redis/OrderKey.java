package com.gageshan.miaosha.redis;

/**
 * Create by gageshan on 2020/5/14 11:41
 */
public class OrderKey extends BasePrefix {
    public static final int TOKEN_EXPIRE = 60;
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }
    public OrderKey(String prefix) {
        super(prefix);
    }
    public static OrderKey getOrder = new OrderKey("MSOrder");
}
