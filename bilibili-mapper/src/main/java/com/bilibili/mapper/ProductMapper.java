package com.bilibili.mapper;

import com.bilibili.entity.Product;

import java.util.List;

public interface ProductMapper {
    List<Product> queryAll();
    Product queryById(Integer id);
    Integer updateProduct(Product product);
}
