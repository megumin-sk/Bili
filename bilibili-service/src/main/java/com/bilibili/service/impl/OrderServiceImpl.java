package com.bilibili.service.impl;

import com.bilibili.entity.OrderItem;
import com.bilibili.entity.Product;
import com.bilibili.mapper.OrderMapper;
import com.bilibili.mapper.ProductMapper;
import com.bilibili.service.OrderService;
import com.bilibili.utils.ResponseEnum;
import com.bilibili.utils.ResponseUtil;
import com.bilibili.utils.UserContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private ProductMapper productMapper;

    //添加订单
    @Transactional
    @Override
    public ResponseUtil addOrder(OrderItem orderItem) throws ParseException {
        //通过订单中的商品id查询商品
        Product product = productMapper.queryById(orderItem.getProductId());
        //通过商品Id和用户Id查询订单
        List<OrderItem> itemList = orderMapper.queryById(UserContext.getUserId(),orderItem.getProductId());
        if (!itemList.isEmpty()){
            OrderItem item = itemList.get(0);
            item.setBuyNum(item.getBuyNum() + orderItem.getBuyNum());
            orderItem = item;
            //修改订单中的购买量
            orderMapper.update(orderItem);
            return ResponseUtil.get(ResponseEnum.ORDER_UPDATE_SUCCESS,orderItem);
        }
        //判断商品库存是否充足
        if(product.getStoreNum() < orderItem.getBuyNum()){
            return ResponseUtil.get(ResponseEnum.PRODUCT_NOT_ENOUGH);
        }else {
//            //修改商品库存
//            product.setStoreNum(product.getStoreNum() - orderItem.getBuyNum());
//            productMapper.updateProduct(product);
            //设置订单信息
            orderItem.setCreateTime(new Date());
            orderItem.setPrice(product.getPrice());
            orderItem.setUserId(UserContext.getUserId());
            //执行添加订单
            int result = orderMapper.addOrder(orderItem);
            if (result > 0){
                return ResponseUtil.get(ResponseEnum.ORDER_ADD_SUCCESS,orderItem);
            }else {
                return ResponseUtil.get(ResponseEnum.ORDER_ADD_FAIL);
            }
        }
    }
}
