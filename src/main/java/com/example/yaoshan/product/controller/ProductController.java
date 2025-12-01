package com.example.yaoshan.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.yaoshan.model.Product;
import com.example.yaoshan.model.ProductCategory;
import com.example.yaoshan.product.dto.ProductQueryDTO;
import com.example.yaoshan.product.service.ProductService;
import com.example.yaoshan.common.Result;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 商品控制器
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 分页查询商品列表
     */
    @PostMapping("/list")
    public Result<IPage<Product>> getProductList(@RequestBody ProductQueryDTO queryDTO) {
        IPage<Product> page = productService.getProductList(queryDTO);
        return Result.success(page);
    }

    /**
     * 根据分类ID查询商品列表
     */
    @GetMapping("/category/{categoryId}")
    public Result<IPage<Product>> getProductsByCategoryId(
            @PathVariable Integer categoryId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        IPage<Product> productPage = productService.getProductsByCategoryId(categoryId, page, size);
        return Result.success(productPage);
    }

    /**
     * 根据ID查询商品详情
     */
    @GetMapping("/{id}")
    public Result<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return Result.success(product);
    }

    /**
     * 获取所有商品分类
     */
    @GetMapping("/categories")
    public Result<List<ProductCategory>> getAllCategories() {
        List<ProductCategory> categories = productService.getAllCategories();
        return Result.success(categories);
    }
}