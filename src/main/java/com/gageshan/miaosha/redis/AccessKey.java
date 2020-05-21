package com.gageshan.miaosha.redis;

/**
 * Create by gageshan on 2020/5/14 11:41
 */
public class AccessKey extends BasePrefix {
    public static final int TOKEN_EXPIRE = 3600*24*2;
    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }
    public static AccessKey access = new AccessKey(5,"access");

    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds,"access");
    }
}
