package com.example.yaoshan.order.dto;

import lombok.Data;

/**
 * 订单商品DTO
 */
@Data
public class OrderProductDTO {
    private Long productId;        // 商品ID
    private Integer quantity;      // 购买数量
}
