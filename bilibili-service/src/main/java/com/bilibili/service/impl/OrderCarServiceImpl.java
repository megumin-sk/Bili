package com.bilibili.service.impl;

import com.bilibili.entity.OrderItem;
import com.bilibili.entity.Product;
import com.bilibili.entity.ShoppingCar;
import com.bilibili.mapper.OrderCarMapper;
import com.bilibili.mapper.OrderMapper;
import com.bilibili.mapper.ProductMapper;
import com.bilibili.service.OrderCarService;
import com.bilibili.utils.ResponseEnum;
import com.bilibili.utils.ResponseUtil;
import com.bilibili.utils.UserContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@Service("orderCarService")
public class OrderCarServiceImpl implements OrderCarService {
    @Resource
    private OrderCarMapper orderCarMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private ProductMapper productMapper;
    @Override
    public ResponseUtil addOrderCar(ShoppingCar shoppingCar) throws ParseException {
        double totalPrice = 0;
        List<Integer> ids = orderMapper.queryIds(UserContext.getUserId());
        for (Integer id : ids) {
            Product product = productMapper.queryById(id);
            OrderItem orderItem = orderMapper.queryById(UserContext.getUserId(), id).get(0);
            if (product.getStoreNum() < orderItem.getBuyNum()){
                return ResponseUtil.get(ResponseEnum.PRODUCT_NOT_ENOUGH);
            }else {
                totalPrice += orderItem.getPrice();
            }
        }
        shoppingCar.setTotalPrice(totalPrice);
        int result = orderCarMapper.addOrderCar(shoppingCar);
        if (result <= 0){
            return ResponseUtil.get(ResponseEnum.ORDER_ADD_FAIL);
        }else {
            return ResponseUtil.get(ResponseEnum.ORDER_ADD_SUCCESS);
        }
    }
}
