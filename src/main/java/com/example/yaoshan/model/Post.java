package com.example.yaoshan.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 社区帖子实体类
 */
@Data
@TableName("post")
public class Post {
    @TableId(type = IdType.AUTO)
    private Long id;              // 帖子ID
    private String openid;        // 用户openid
    private String title;         // 帖子标题
    private String content;       // 帖子内容
    private String images;        // 图片URL，逗号分隔
    private Integer viewCount;    // 浏览量
    private Integer likeCount;    // 点赞数
    private Integer commentCount; // 评论数
    private Integer status;       // 状态：0-正常 1-置顶 2-精华 3-删除
    private Date createTime;      // 创建时间
    private Date updateTime;      // 更新时间
}