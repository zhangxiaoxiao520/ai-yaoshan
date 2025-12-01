package com.example.yaoshan.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.yaoshan.model.Product;
import com.example.yaoshan.product.dto.ProductQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品Mapper接口
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    
    /**
     * 分页查询商品列表
     */
    IPage<Product> selectProductList(IPage<Product> page, @Param("query") ProductQueryDTO queryDTO);
    
    /**
     * 根据分类ID查询商品列表
     */
    IPage<Product> selectByCategoryId(IPage<Product> page, @Param("categoryId") Integer categoryId);
    
    /**
     * 根据ID查询商品详情
     */
    Product selectById(@Param("id") Long id);
    
    /**
     * 减少库存
     */
    int decreaseStock(@Param("id") Long id, @Param("num") Integer num);
}