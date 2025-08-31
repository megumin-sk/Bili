package com.bilibili.mapper;

import com.bilibili.entity.CartItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    Integer addOrder(CartItem cartItem);
    List<CartItem> queryById(@Param("userId") Integer userId, @Param("productId") Integer productId);
    List<Integer> queryIds(@Param("userId") Integer userId);
    Integer update(CartItem cartItem);
    List<CartItem> queryByUserId(Integer userId);
}

