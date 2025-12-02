package com.example.yaoshan.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 点赞实体类
 */
@Data
@TableName("likes")
public class Like {
    @TableId(type = IdType.AUTO)
    // 主键
    
    private Long id;         // 点赞ID
    private String openid;   // 用户openid
    private Long targetId;   // 目标ID（帖子ID或评论ID）
    private Integer type;    // 类型：1-帖子点赞 2-评论点赞
    private Date createTime; // 创建时间
}