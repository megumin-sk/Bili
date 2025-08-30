package com.bilibili.mapper;

import com.bilibili.entity.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    Integer addOrder(OrderItem orderItem);
    List<OrderItem> queryById(@Param("userId") Integer userId, @Param("productId") Integer productId);
    List<Integer> queryIds(@Param("userId") Integer userId);
    Integer update(OrderItem orderItem);
}
