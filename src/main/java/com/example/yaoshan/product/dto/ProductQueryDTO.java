package com.example.yaoshan.product.dto;

import lombok.Data;

/**
 * 商品查询DTO
 */
@Data
public class ProductQueryDTO {
    private Integer categoryId; // 分类ID
    private String keyword;     // 搜索关键词
    private Integer page = 1;   // 页码
    private Integer size = 10;  // 每页数量
}