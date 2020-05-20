package com.gageshan.miaosha.redis;

/**
 * Create by gageshan on 2020/5/14 11:37
 */
public abstract class BasePrefix implements KeyPrefix{

    private int expireSeconds;
    private String prefix;

    public BasePrefix(String prefix) {
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public int getExpireSeconds() {
        return this.expireSeconds;
    }
    public String getPrefix() {
        String name = getClass().getSimpleName();
        return name + ":" + this.prefix;
    }
}
