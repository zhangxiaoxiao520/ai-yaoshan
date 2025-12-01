package com.example.yaoshan.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户实体类
 */
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;         // 用户ID
    private String openid;   // 微信openid
    private String nickname; // 昵称
    private String avatar;   // 头像URL
    private Integer gender;  // 性别 0:未知 1:男 2:女
    private String phone;    // 手机号
    private String address;  // 地址
    private Integer age;     // 年龄
    private String healthInfo; // 健康信息
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
}