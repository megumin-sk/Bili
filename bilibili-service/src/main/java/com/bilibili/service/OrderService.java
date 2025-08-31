package com.bilibili.service;

import com.bilibili.entity.CartItem;
import com.bilibili.utils.ResponseUtil;

import java.text.ParseException;

public interface OrderService {
    ResponseUtil addToCart(CartItem cartItem) throws ParseException;
    ResponseUtil getCartItems() throws ParseException;
}
