package com.example.yaoshan.user.dto;

import lombok.Data;

/**
 * 用户信息更新请求DTO
 */
@Data
public class UpdateUserDTO {
    private String nickname;  // 昵称
    private String avatar;    // 头像URL
    private Integer gender;   // 性别
    private String phone;     // 手机号
    private String address;   // 地址
    private Integer age;      // 年龄
    private String healthInfo; // 健康信息
}