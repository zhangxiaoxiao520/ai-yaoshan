package com.example.yaoshan.order.dto;

import com.example.yaoshan.model.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单响应DTO
 */
@Data
public class OrderResponseDTO {
    private Long id;                // 订单ID
    private String orderNo;         // 订单号
    private BigDecimal totalAmount; // 订单总金额
    private BigDecimal actualAmount; // 实际支付金额
    private Integer status;         // 订单状态
    private String statusText;      // 状态文本
    private String address;         // 收货地址
    private String phone;           // 联系电话
    private String receiverName;    // 收货人姓名
    private String remark;          // 订单备注
    private Date paymentTime;       // 支付时间
    private Date deliveryTime;      // 发货时间
    private Date receiveTime;       // 收货时间
    private Date createTime;        // 创建时间
    private List<OrderItem> orderItems; // 订单项列表
}