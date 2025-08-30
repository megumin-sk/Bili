package com.bilibili.controller;

import com.bilibili.service.ProductService;
import com.bilibili.utils.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductService productService;

    //查询全部商品
    @GetMapping("/queryAll")
    public ResponseUtil queryAll() {
        return productService.queryAll();
    }

    //根据id查询商品
    @GetMapping("/queryById/{id}")
    public ResponseUtil queryById(@PathVariable("id") Integer id) {
        return productService.queryById(id);
    }
}
