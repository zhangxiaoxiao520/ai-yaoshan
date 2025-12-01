package com.example.yaoshan.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单项实体类
 */
@Data
@TableName("order_item")
public class OrderItem {
    @TableId(type = IdType.AUTO)
    private Long id;              // 订单项ID
    private String orderNo;       // 订单号
    private Long productId;       // 商品ID
    private String productName;   // 商品名称
    private String mainImage;     // 商品图片
    private BigDecimal price;     // 商品单价
    private Integer quantity;     // 购买数量
    private BigDecimal totalPrice; // 小计金额
    private Date createTime;      // 创建时间
    private Date updateTime;      // 更新时间
}