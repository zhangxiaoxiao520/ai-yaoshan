package com.example.yaoshan.product.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.yaoshan.model.Product;
import com.example.yaoshan.model.ProductCategory;
import com.example.yaoshan.product.dto.ProductQueryDTO;
import com.example.yaoshan.product.mapper.ProductCategoryMapper;
import com.example.yaoshan.product.mapper.ProductMapper;
import com.example.yaoshan.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 商品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public IPage<Product> getProductList(ProductQueryDTO queryDTO) {
        Page<Product> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());
        return productMapper.selectProductList(page, queryDTO);
    }

    @Override
    public IPage<Product> getProductsByCategoryId(Integer categoryId, Integer page, Integer size) {
        Page<Product> productPage = new Page<>(page, size);
        return productMapper.selectByCategoryId(productPage, categoryId);
    }

    @Override
    public Product getProductById(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public List<ProductCategory> getAllCategories() {
        return productCategoryMapper.selectAllCategories();
    }

    @Transactional
    @Override
    public boolean decreaseStock(Long productId, Integer num) {
        return productMapper.decreaseStock(productId, num) > 0;
    }
}