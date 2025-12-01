package com.example.yaoshan.order.dto;

import lombok.Data;

/**
 * 订单查询DTO
 */
@Data
public class OrderQueryDTO {
    private String openid;         // 用户openid
    private Integer status;        // 订单状态：0-待支付 1-待发货 2-待收货 3-已完成 4-已取消
    private String orderNo;        // 订单号
    private Integer page = 1;      // 页码
    private Integer size = 10;     // 每页数量
}