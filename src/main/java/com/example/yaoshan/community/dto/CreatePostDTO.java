package com.example.yaoshan.community.dto;

import lombok.Data;

import java.util.List;

/**
 * 创建帖子请求DTO
 */
@Data
public class CreatePostDTO {
    private String title;      // 帖子标题
    private String content;    // 帖子内容
    private List<String> images; // 图片URL列表
}