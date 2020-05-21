package com.gageshan.miaosha.redis;

/**
 * Create by gageshan on 2020/5/14 11:41
 */
public class MiaoshaKey extends BasePrefix {
    public static final int TOKEN_EXPIRE = 3600*24*2;
    public MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }

    public MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("isOver");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(300,"isOver");
    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300,"vc");
}
