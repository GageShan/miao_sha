package com.gageshan.miaosha.result;

import lombok.Data;

/**
 * Create by gageshan on 2020/5/14 22:12
 */
@Data
public class CodeMsg {

    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101,"参数校验异常:%s");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500102,"用户不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500103,"密码错误");
    public static  CodeMsg SESSION_ERROR = new CodeMsg(500104,"session不存在");

    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500201,"秒杀商品售空");
    public static CodeMsg REPEATE_MIAOSHA = new CodeMsg(500202,"已秒杀该商品");


    public static  CodeMsg ORDER_NOT_EXIST = new CodeMsg(500301,"订单不存在");
    public CodeMsg fillArgs(Object...args) {
        String format = String.format(this.msg, args);
        return new CodeMsg(this.code,format);
    }
    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
