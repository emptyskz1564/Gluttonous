package com.taotie.wechatpro.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 创建时间: 2020/4/5 11:07
 * 文件备注:
 * 编写人员:杨伯益
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    public ErrorMessage showErrorMessage(MyException e){
        return new ErrorMessage(e.getCode(),e.getMessage());
    }
}
