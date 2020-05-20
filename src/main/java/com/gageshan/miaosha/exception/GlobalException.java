package com.gageshan.miaosha.exception;

import com.gageshan.miaosha.result.CodeMsg;
import com.sun.org.apache.bcel.internal.classfile.Code;

/**
 * Create by gageshan on 2020/5/14 23:24
 */
public class GlobalException extends RuntimeException {
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
