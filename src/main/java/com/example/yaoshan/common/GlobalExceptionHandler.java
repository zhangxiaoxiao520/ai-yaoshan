package com.example.yaoshan.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 处理自定义异常
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result<?> handleBusinessException(BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    // 处理IO异常
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public Result<?> handleIOException(IOException e) {
        return Result.error(500, "文件操作失败：" + e.getMessage());
    }

    // 处理其他异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> handleException(Exception e) {
        return Result.error(500, "服务器内部错误：" + e.getMessage());
    }
}