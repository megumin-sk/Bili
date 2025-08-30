package com.bilibili.service;

import com.bilibili.entity.OrderItem;
import com.bilibili.utils.ResponseUtil;

import java.text.ParseException;

public interface OrderService {
    ResponseUtil addOrder(OrderItem orderItem) throws ParseException;
}
