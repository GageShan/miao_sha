package com.gageshan.miaosha.exception;

import com.gageshan.miaosha.result.CodeMsg;
import com.gageshan.miaosha.result.Result;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Create by gageshan on 2020/5/14 23:21
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if(e instanceof GlobalException) {
            GlobalException globalException = (GlobalException)e;
            return Result.error(globalException.getCodeMsg());
        } else if(e instanceof BindException) {
            BindException bindException = (BindException)e;
            List<ObjectError> allErrors = bindException.getAllErrors();
            ObjectError error = allErrors.get(0);
            String errorDefaultMessage = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(errorDefaultMessage));
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
