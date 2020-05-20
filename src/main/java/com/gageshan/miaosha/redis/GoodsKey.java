package com.gageshan.miaosha.redis;

/**
 * Create by gageshan on 2020/5/14 11:41
 */
public class GoodsKey extends BasePrefix {
    public static final int TOKEN_EXPIRE = 60;
    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }
    public static GoodsKey getGoodsList = new GoodsKey(TOKEN_EXPIRE,"GL");
    public static GoodsKey getGoodsDetail = new GoodsKey(TOKEN_EXPIRE,"GD");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0,"gs");
}
