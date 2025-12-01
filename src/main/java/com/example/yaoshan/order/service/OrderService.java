package com.example.yaoshan.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.yaoshan.model.Order;
import com.example.yaoshan.order.dto.CreateOrderDTO;
import com.example.yaoshan.order.dto.OrderQueryDTO;
import com.example.yaoshan.order.dto.OrderResponseDTO;

/**
 * 订单Service接口
 */
public interface OrderService {
    
    /**
     * 创建订单
     */
    OrderResponseDTO createOrder(String openid, CreateOrderDTO createOrderDTO);
    
    /**
     * 获取订单详情
     */
    OrderResponseDTO getOrderDetail(String openid, String orderNo);
    
    /**
     * 查询订单列表
     */
    IPage<OrderResponseDTO> getOrderList(OrderQueryDTO queryDTO);
    
    /**
     * 取消订单
     */
    boolean cancelOrder(String openid, String orderNo);
    
    /**
     * 支付订单
     */
    boolean payOrder(String orderNo, String paymentMethod);
    
    /**
     * 确认收货
     */
    boolean confirmReceive(String openid, String orderNo);
    
    /**
     * 发货
     */
    boolean deliveryOrder(String orderNo, String trackingNumber, String shippingCompany);
}