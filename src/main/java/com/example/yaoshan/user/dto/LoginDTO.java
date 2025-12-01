package com.example.yaoshan.user.dto;

import lombok.Data;

/**
 * 用户登录请求DTO
 */
@Data
public class LoginDTO {
    private String code; // 微信登录code
}