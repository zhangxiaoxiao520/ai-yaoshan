package com.example.yaoshan.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.yaoshan.model.Order;
import com.example.yaoshan.model.OrderItem;
import com.example.yaoshan.order.dto.CreateOrderDTO;
import com.example.yaoshan.order.dto.OrderQueryDTO;
import com.example.yaoshan.order.dto.OrderResponseDTO;
import com.example.yaoshan.order.mapper.OrderItemMapper;
import com.example.yaoshan.order.mapper.OrderMapper;
import com.example.yaoshan.order.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 订单Service实现类
 */
@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private OrderItemMapper orderItemMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderResponseDTO createOrder(String openid, CreateOrderDTO createOrderDTO) {
        // 生成订单号
        String orderNo = generateOrderNo();
        
        // 计算总金额
        BigDecimal totalAmount = new BigDecimal("0");
        List<OrderItem> orderItems = createOrderDTO.getProducts().stream().map(product -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderNo(orderNo);
            orderItem.setProductId(product.getProductId());
            orderItem.setQuantity(product.getQuantity());
            // 这里应该从商品服务获取商品信息，暂时设置默认值
            orderItem.setProductName("商品" + product.getProductId());
            orderItem.setMainImage("/images/default.jpg");
            orderItem.setPrice(new BigDecimal("99.99"));
            orderItem.setTotalPrice(orderItem.getPrice().multiply(new BigDecimal(product.getQuantity())));
            orderItem.setCreateTime(new Date());
            orderItem.setUpdateTime(new Date());
            return orderItem;
        }).collect(Collectors.toList());
        
        // 计算总金额
        for (OrderItem item : orderItems) {
            totalAmount = totalAmount.add(item.getTotalPrice());
        }
        
        // 创建订单
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setOpenid(openid);
        order.setTotalAmount(totalAmount);
        order.setActualAmount(totalAmount); // 暂时没有优惠
        order.setStatus(0); // 待支付
        order.setAddress(createOrderDTO.getAddress());
        order.setPhone(createOrderDTO.getPhone());
        order.setReceiverName(createOrderDTO.getReceiverName());
        order.setRemark(createOrderDTO.getRemark());
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        
        // 保存订单和订单项
        orderMapper.insert(order);
        orderItemMapper.batchInsert(orderItems);
        
        // 构建响应DTO
        return buildOrderResponseDTO(order, orderItems);
    }
    
    @Override
    public OrderResponseDTO getOrderDetail(String openid, String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getOpenid().equals(openid)) {
            return null;
        }
        
        List<OrderItem> orderItems = orderItemMapper.selectByOrderNo(orderNo);
        return buildOrderResponseDTO(order, orderItems);
    }
    
    @Override
    public IPage<OrderResponseDTO> getOrderList(OrderQueryDTO queryDTO) {
        IPage<Order> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());
        IPage<Order> orderPage = orderMapper.selectOrderList(page, queryDTO);
        
        return orderPage.convert(order -> {
            List<OrderItem> orderItems = orderItemMapper.selectByOrderNo(order.getOrderNo());
            return buildOrderResponseDTO(order, orderItems);
        });
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelOrder(String openid, String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getOpenid().equals(openid) || order.getStatus() != 0) {
            return false;
        }
        
        return orderMapper.updateOrderStatus(orderNo, 4) > 0; // 4-已取消
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(String orderNo, String paymentMethod) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || order.getStatus() != 0) {
            return false;
        }
        
        return orderMapper.updatePaymentInfo(orderNo, paymentMethod, new Date()) > 0
                && orderMapper.updateOrderStatus(orderNo, 1) > 0; // 1-待发货
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmReceive(String openid, String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getOpenid().equals(openid) || order.getStatus() != 2) {
            return false;
        }
        
        return orderMapper.updateOrderStatus(orderNo, 3) > 0; // 3-已完成
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deliveryOrder(String orderNo, String trackingNumber, String shippingCompany) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || order.getStatus() != 1) {
            return false;
        }
        
        // 更新订单发货信息
        order.setTrackingNumber(trackingNumber);
        order.setShippingCompany(shippingCompany);
        order.setDeliveryTime(new Date());
        order.setStatus(2); // 2-待收货
        order.setUpdateTime(new Date());
        
        return orderMapper.updateById(order) > 0;
    }
    
    /**
     * 生成订单号
     */
    private String generateOrderNo() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = UUID.randomUUID().toString().substring(0, 8);
        return timestamp + random;
    }
    
    /**
     * 构建订单响应DTO
     */
    private OrderResponseDTO buildOrderResponseDTO(Order order, List<OrderItem> orderItems) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        BeanUtils.copyProperties(order, responseDTO);
        
        // 设置状态文本
        switch (order.getStatus()) {
            case 0: responseDTO.setStatusText("待支付"); break;
            case 1: responseDTO.setStatusText("待发货"); break;
            case 2: responseDTO.setStatusText("待收货"); break;
            case 3: responseDTO.setStatusText("已完成"); break;
            case 4: responseDTO.setStatusText("已取消"); break;
            default: responseDTO.setStatusText("未知状态");
        }
        
        responseDTO.setOrderItems(orderItems);
        return responseDTO;
    }
}