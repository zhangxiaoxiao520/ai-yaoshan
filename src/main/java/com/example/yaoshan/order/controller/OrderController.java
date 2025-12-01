package com.example.yaoshan.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.yaoshan.common.Result;
import com.example.yaoshan.order.dto.CreateOrderDTO;
import com.example.yaoshan.order.dto.OrderQueryDTO;
import com.example.yaoshan.order.dto.OrderResponseDTO;
import com.example.yaoshan.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    /**
     * 创建订单
     */
    @PostMapping("/create")
    public Result<OrderResponseDTO> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        String openid = getCurrentUserOpenid();
        OrderResponseDTO order = orderService.createOrder(openid, createOrderDTO);
        return Result.success(order);
    }
    
    /**
     * 获取订单详情
     */
    @GetMapping("/detail/{orderNo}")
    public Result<OrderResponseDTO> getOrderDetail(@PathVariable String orderNo) {
        String openid = getCurrentUserOpenid();
        OrderResponseDTO order = orderService.getOrderDetail(openid, orderNo);
        if (order == null) {
            return Result.error("订单不存在");
        }
        return Result.success(order);
    }
    
    /**
     * 查询订单列表
     */
    @GetMapping("/list")
    public Result<IPage<OrderResponseDTO>> getOrderList(OrderQueryDTO queryDTO) {
        String openid = getCurrentUserOpenid();
        queryDTO.setOpenid(openid);
        IPage<OrderResponseDTO> orderPage = orderService.getOrderList(queryDTO);
        return Result.success(orderPage);
    }
    
    /**
     * 取消订单
     */
    @PutMapping("/cancel/{orderNo}")
    public Result<Boolean> cancelOrder(@PathVariable String orderNo) {
        String openid = getCurrentUserOpenid();
        boolean success = orderService.cancelOrder(openid, orderNo);
        if (!success) {
            return Result.error("取消订单失败");
        }
        return Result.success(true);
    }
    
    /**
     * 确认收货
     */
    @PutMapping("/confirm/{orderNo}")
    public Result<Boolean> confirmReceive(@PathVariable String orderNo) {
        String openid = getCurrentUserOpenid();
        boolean success = orderService.confirmReceive(openid, orderNo);
        if (!success) {
            return Result.error("确认收货失败");
        }
        return Result.success(true);
    }
    
    /**
     * 支付订单（模拟）
     */
    @PutMapping("/pay/{orderNo}")
    public Result<Boolean> payOrder(@PathVariable String orderNo) {
        boolean success = orderService.payOrder(orderNo, "微信支付");
        if (!success) {
            return Result.error("支付失败");
        }
        return Result.success(true);
    }
    
    /**
     * 获取当前登录用户的openid
     */
    private String getCurrentUserOpenid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new RuntimeException("用户未登录");
    }
}