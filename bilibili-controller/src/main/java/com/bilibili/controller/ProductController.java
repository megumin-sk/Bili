package com.bilibili.controller;

import com.bilibili.service.ProductService;
import com.bilibili.utils.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 商品控制器
 * 
 * <p>处理商品相关的HTTP请求，包括查询全部商品和根据ID查询商品。</p>
 * 
 * @author wang
 * @version 0.0.1
 * @since 2025-04-05
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    /** 商品服务层接口 */
    @Resource
    private ProductService productService;

    /**
     * 查询全部商品
     * 
     * <p>处理GET请求，调用商品服务层的queryAll方法查询全部商品。</p>
     * 
     * @return ResponseUtil 包含查询结果的响应工具类
     */
    @GetMapping("/queryAll")
    public ResponseUtil queryAll() {
        return productService.queryAll();
    }

    /**
     * 根据ID查询商品
     * 
     * <p>处理GET请求，调用商品服务层的queryById方法根据ID查询商品。</p>
     * 
     * @param id 商品ID
     * @return ResponseUtil 包含查询结果的响应工具类
     */
    @GetMapping("/queryById/{id}")
    public ResponseUtil queryById(@PathVariable("id") Integer id) {
        return productService.queryById(id);
    }
}
