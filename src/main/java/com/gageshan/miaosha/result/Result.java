package com.gageshan.miaosha.result;

import lombok.Data;

/**
 * Create by gageshan on 2020/5/14 22:10
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg);
    }

    public Result(T data) {
        this.data = data;
    }
    public Result(CodeMsg codeMsg) {
        if(codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
