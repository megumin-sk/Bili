package com.bilibili.controller;

import com.bilibili.entity.OrderItem;
import com.bilibili.entity.ShoppingCar;
import com.bilibili.service.OrderCarService;
import com.bilibili.service.OrderService;
import com.bilibili.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderCarService orderCarService;

    @GetMapping("/queryOrder")
    public ResponseUtil queryOrder() throws ParseException {
        return orderService.queryOrder();
    }
    @PostMapping("/addOrder")
    public ResponseUtil addOrder(@RequestBody OrderItem orderItem) throws ParseException {
        return orderService.addOrder(orderItem);
    }

    @PostMapping("/addShopCar")
    public ResponseUtil addShopCar(@RequestBody ShoppingCar shoppingCar) throws ParseException {
        return orderCarService.addOrderCar(shoppingCar);
    }
}
