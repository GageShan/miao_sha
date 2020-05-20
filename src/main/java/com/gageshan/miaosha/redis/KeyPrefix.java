package com.gageshan.miaosha.redis;

/**
 * Create by gageshan on 2020/5/14 11:32
 */
public interface KeyPrefix {
    int getExpireSeconds();
    String getPrefix();
}
