package com.bilibili.service.impl;

import com.bilibili.entity.Product;
import com.bilibili.mapper.ProductMapper;
import com.bilibili.service.ProductService;
import com.bilibili.utils.ResponseEnum;
import com.bilibili.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    // 查询全部商品
    @Override
    public ResponseUtil queryAll() {
        List<Product> products = productMapper.queryAll();
        if (!products.isEmpty()){
            return ResponseUtil.get(ResponseEnum.PRODUCT_QUERY_SUCCESS,products);
        }else {
            return ResponseUtil.get(ResponseEnum.PRODUCT_QUERY_FAIL);
        }
    }

    // 查询商品详情
    @Override
    public ResponseUtil queryById(Integer id) {
        Product product = productMapper.queryById(id);
        if (product != null){
            return ResponseUtil.get(ResponseEnum.PRODUCT_DETAIL_SUCCESS,product);
        }else {
            return ResponseUtil.get(ResponseEnum.PRODUCT_DETAIL_FAIL);
        }
    }
}
