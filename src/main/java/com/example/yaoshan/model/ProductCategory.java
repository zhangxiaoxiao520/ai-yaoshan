package com.example.yaoshan.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 商品分类实体类
 */
@Data
@TableName("product_category")
public class ProductCategory {
    @TableId(type = IdType.AUTO)
    private Long id;           // 分类ID
    private String name;       // 分类名称
    private String icon;       // 分类图标
    private Integer sort;      // 排序
    private Date createTime;   // 创建时间
    private Date updateTime;   // 更新时间
}