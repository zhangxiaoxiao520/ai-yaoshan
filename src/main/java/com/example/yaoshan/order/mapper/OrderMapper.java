package com.example.yaoshan.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.yaoshan.model.Order;
import com.example.yaoshan.order.dto.OrderQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单Mapper接口
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
    /**
     * 分页查询订单列表
     */
    IPage<Order> selectOrderList(IPage<Order> page, @Param("query") OrderQueryDTO queryDTO);
    
    /**
     * 根据订单号查询订单
     */
    Order selectByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * 更新订单状态
     */
    int updateOrderStatus(@Param("orderNo") String orderNo, @Param("status") Integer status);
    
    /**
     * 更新订单支付信息
     */
    int updatePaymentInfo(@Param("orderNo") String orderNo, @Param("paymentMethod") String paymentMethod, 
                          @Param("paymentTime") java.util.Date paymentTime);
}