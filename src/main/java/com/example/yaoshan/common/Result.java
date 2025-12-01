package com.example.yaoshan.common;

import lombok.Data;

/**
 * 统一响应结果实体类
 */
@Data
public class Result<T> {
    private int code;       // 响应码
    private String message; // 响应消息
    private T data;         // 响应数据
    
    // 成功响应
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }
    
    // 错误响应
    public static <T> Result<T> error(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    
    // 错误响应（默认错误码）
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }
}