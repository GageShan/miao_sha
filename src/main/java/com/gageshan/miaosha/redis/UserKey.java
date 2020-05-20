package com.gageshan.miaosha.redis;

/**
 * Create by gageshan on 2020/5/14 11:41
 */
public class UserKey extends BasePrefix {
    public static final int TOKEN_EXPIRE = 3600*24*2;
    public UserKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }
    public static UserKey getById = new UserKey(0,"id");
    public static UserKey getToken = new UserKey(TOKEN_EXPIRE,"token");
}
