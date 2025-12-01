package com.example.yaoshan.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yaoshan.model.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品分类Mapper接口
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    
    /**
     * 获取所有分类列表
     */
    List<ProductCategory> selectAllCategories();
}