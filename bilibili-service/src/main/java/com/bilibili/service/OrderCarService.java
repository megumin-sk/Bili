package com.bilibili.service;

import com.bilibili.entity.ShoppingCar;
import com.bilibili.utils.ResponseUtil;

import java.text.ParseException;

public interface OrderCarService {
    ResponseUtil addOrderCar(ShoppingCar shoppingCar) throws ParseException;
}
