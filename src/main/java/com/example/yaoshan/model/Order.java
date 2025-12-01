package com.example.yaoshan.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体类
 */
@Data
@TableName("order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;                // 订单ID
    private String orderNo;         // 订单号
    private String openid;          // 用户openid
    private BigDecimal totalAmount; // 订单总金额
    private BigDecimal actualAmount; // 实际支付金额
    private Integer status;         // 订单状态：0-待支付 1-待发货 2-待收货 3-已完成 4-已取消
    private String address;         // 收货地址
    private String phone;           // 联系电话
    private String receiverName;    // 收货人姓名
    private String remark;          // 订单备注
    private Date paymentTime;       // 支付时间
    private Date deliveryTime;      // 发货时间
    private Date receiveTime;       // 收货时间
    private Date cancelTime;        // 取消时间
    private String paymentMethod;   // 支付方式
    private String trackingNumber;  // 物流单号
    private String shippingCompany; // 物流公司
    private Date createTime;        // 创建时间
    private Date updateTime;        // 更新时间
}