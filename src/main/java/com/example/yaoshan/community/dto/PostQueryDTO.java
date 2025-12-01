package com.example.yaoshan.community.dto;

import lombok.Data;

/**
 * 帖子查询DTO
 */
@Data
public class PostQueryDTO {
    private Integer page = 1;      // 页码
    private Integer size = 10;     // 每页数量
    private String openid;         // 用户openid（可选）
    private Integer status;        // 状态（可选）
    private String keyword;        // 关键词搜索（可选）
    private String sortBy = "createTime"; // 排序字段
    private String order = "desc"; // 排序方向
}