package com.example.yaoshan.order.dto;

import lombok.Data;

import java.util.List;

/**
 * 创建订单请求DTO
 */
@Data
public class CreateOrderDTO {
    private String address;        // 收货地址
    private String phone;          // 联系电话
    private String receiverName;   // 收货人姓名
    private String remark;         // 订单备注
    private List<OrderProductDTO> products; // 订单商品列表
}

