package com.gageshan.miaosha.utils;

import java.util.UUID;

/**
 * Create by gageshan on 2020/5/15 18:03
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
