package com.example.yaoshan.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.yaoshan.model.Product;
import com.example.yaoshan.model.ProductCategory;
import com.example.yaoshan.product.dto.ProductQueryDTO;

import java.util.List;

/**
 * 商品服务接口
 */
public interface ProductService {
    
    /**
     * 分页查询商品列表
     */
    IPage<Product> getProductList(ProductQueryDTO queryDTO);
    
    /**
     * 根据分类ID查询商品列表
     */
    IPage<Product> getProductsByCategoryId(Integer categoryId, Integer page, Integer size);
    
    /**
     * 根据ID查询商品详情
     */
    Product getProductById(Long id);
    
    /**
     * 获取所有商品分类
     */
    List<ProductCategory> getAllCategories();
    
    /**
     * 减少商品库存
     */
    boolean decreaseStock(Long productId, Integer num);
}