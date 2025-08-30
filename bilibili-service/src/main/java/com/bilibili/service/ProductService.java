package com.bilibili.service;

import com.bilibili.utils.ResponseUtil;

public interface ProductService {
    ResponseUtil queryAll();
    ResponseUtil queryById(Integer id);
}
