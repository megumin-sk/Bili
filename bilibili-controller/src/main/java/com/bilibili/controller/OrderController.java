package com.bilibili.controller;

import com.bilibili.entity.CartItem;
import com.bilibili.service.OrderService;
import com.bilibili.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 订单控制器
 * 
 * <p>处理订单相关的HTTP请求，包括查询购物车商品和添加商品到购物车。</p>
 * 
 * @author wang
 * @version 0.0.1
 * @since 2025-04-05
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    /** 订单服务层接口 */
    @Resource
    private OrderService orderService;

    /**
     * 查询购物车商品列表
     * 
     * <p>处理GET请求，调用订单服务层的getCartItems方法查询购物车商品列表。</p>
     * 
     * @return ResponseUtil 包含购物车商品列表的查询结果
     * @throws ParseException 用户ID解析异常
     */
    @GetMapping("/cart/items")
    public ResponseUtil getCartItems() throws ParseException {
        return orderService.getCartItems();
    }
    /**
     * 添加商品到购物车
     * 
     * <p>处理POST请求，调用订单服务层的addToCart方法添加商品到购物车。</p>
     * 
     * @param cartItem 购物车商品信息
     * @return ResponseUtil 包含添加结果的响应工具类
     * @throws ParseException 用户ID解析异常
     */
    @PostMapping("/cart/add")
    public ResponseUtil addToCart(@RequestBody CartItem cartItem) throws ParseException {
        return orderService.addToCart(cartItem);
    }
}

