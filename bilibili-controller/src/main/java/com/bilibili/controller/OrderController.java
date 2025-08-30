package com.bilibili.controller;

import com.bilibili.entity.OrderItem;
import com.bilibili.service.OrderService;
import com.bilibili.utils.ResponseUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @PostMapping("/addOrder")
    public ResponseUtil addOrder(@RequestBody OrderItem orderItem) throws ParseException {
        return orderService.addOrder(orderItem);
    }
}
