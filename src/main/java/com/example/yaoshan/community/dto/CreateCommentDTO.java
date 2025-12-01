package com.example.yaoshan.community.dto;

import lombok.Data;

/**
 * 创建评论请求DTO
 */
@Data
public class CreateCommentDTO {
    private Long postId;     // 帖子ID
    private Long parentId;   // 父评论ID，0表示顶级评论
    private String content;  // 评论内容
}