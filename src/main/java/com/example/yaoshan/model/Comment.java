package com.example.yaoshan.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 评论实体类
 */
@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;           // 评论ID
    private Long postId;       // 帖子ID
    private String openid;     // 用户openid
    private Long parentId;     // 父评论ID，0表示顶级评论
    private String content;    // 评论内容
    private Integer likeCount; // 点赞数
    private Integer status;    // 状态：0-正常 1-删除
    private Date createTime;   // 创建时间
    private Date updateTime;   // 更新时间
}