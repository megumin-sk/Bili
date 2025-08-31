package com.bilibili.mapper;

import com.bilibili.entity.ShoppingCar;

import java.util.List;

public interface OrderCarMapper {
    Integer addOrderCar(ShoppingCar shoppingCar);
    List<ShoppingCar> queryById(Integer userId);
}
