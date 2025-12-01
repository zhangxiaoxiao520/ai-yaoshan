package com.example.yaoshan.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yaoshan.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单项Mapper接口
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    
    /**
     * 根据订单号查询订单项列表
     */
    List<OrderItem> selectByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * 批量插入订单项
     */
    int batchInsert(@Param("orderItems") List<OrderItem> orderItems);
    
    /**
     * 根据订单号删除订单项
     */
    int deleteByOrderNo(@Param("orderNo") String orderNo);
}