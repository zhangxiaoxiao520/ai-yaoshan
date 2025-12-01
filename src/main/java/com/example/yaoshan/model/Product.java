package com.example.yaoshan.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品实体类
 */
@Data
@TableName("product")
public class Product {
    @TableId(type = IdType.AUTO)
    private Long id;             // 商品ID
    private String name;         // 商品名称
    private String description;  // 商品描述
    private BigDecimal price;    // 商品价格
    private Integer stock;       // 库存数量
    private String image;        // 商品图片URL
    private Integer categoryId;  // 分类ID
    private String categoryName; // 分类名称
    private Integer status;      // 商品状态 0:下架 1:上架
    private Integer sales;       // 销量
    private Date createTime;     // 创建时间
    private Date updateTime;     // 更新时间
}