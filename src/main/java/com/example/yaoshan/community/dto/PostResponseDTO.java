package com.example.yaoshan.community.dto;

import com.example.yaoshan.model.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 帖子响应DTO
 */
@Data
public class PostResponseDTO {
    private Long id;              // 帖子ID
    private String openid;        // 用户openid
    private String nickname;      // 用户昵称
    private String avatar;        // 用户头像
    private String title;         // 帖子标题
    private String content;       // 帖子内容
    private List<String> images;  // 图片URL列表
    private Integer viewCount;    // 浏览量
    private Integer likeCount;    // 点赞数
    private Integer commentCount; // 评论数
    private Integer status;       // 状态
    private String statusText;    // 状态文本
    private boolean isLiked;      // 当前用户是否点赞
    private Date createTime;      // 创建时间
    private Date updateTime;      // 更新时间
    private List<CommentDTO> comments; // 评论列表（可选）
    
    /**
     * 评论DTO
     */
    @Data
    public static class CommentDTO {
        private Long id;           // 评论ID
        private Long postId;       // 帖子ID
        private String openid;     // 用户openid
        private String nickname;   // 用户昵称
        private String avatar;     // 用户头像
        private Long parentId;     // 父评论ID
        private String content;    // 评论内容
        private Integer likeCount; // 点赞数
        private boolean isLiked;   // 当前用户是否点赞
        private Date createTime;   // 创建时间
        private List<CommentDTO> replies; // 回复列表
    }
}