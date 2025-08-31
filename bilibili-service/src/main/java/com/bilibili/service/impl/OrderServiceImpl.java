package com.bilibili.service.impl;

import com.bilibili.entity.CartItem;
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

/**
 * 订单服务实现类
 * 
 * <p>该类实现了订单相关的业务逻辑，包括：</p>
 * <ul>
 *   <li>添加商品到购物车</li>
 *   <li>查询购物车商品列表</li>
 * </ul>
 * 
 * <p>使用Spring事务管理确保数据一致性，通过UserContext获取当前登录用户信息。</p>
 * 
 * @author bilibili
 * @version 1.0
 * @since 2024
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    /**
     * 订单数据访问层接口
     */
    @Resource
    private OrderMapper orderMapper;
    
    /**
     * 商品数据访问层接口
     */
    @Resource
    private ProductMapper productMapper;

    /**
     * 添加商品到购物车
     * 
     * <p>添加流程：</p>
     * <ol>
     *   <li>根据商品ID查询商品信息</li>
     *   <li>检查商品库存是否充足</li>
     *   <li>如果购物车中已存在该商品，则更新数量</li>
     *   <li>如果购物车中不存在该商品，则添加新商品</li>
     *   <li>使用Spring事务管理确保数据一致性</li>
     * </ol>
     * 
     * @param cartItem 包含商品ID和数量的购物车项对象
     * @return ResponseUtil 添加结果响应
     * @throws ParseException 用户ID解析异常
     */
    @Transactional
    @Override
    public ResponseUtil addToCart(CartItem cartItem) throws ParseException {
        //通过订单中的商品id查询商品
        Product product = productMapper.queryById(cartItem.getProductId());
        //通过商品Id和用户Id查询订单
        List<CartItem> itemList = orderMapper.queryById(UserContext.getUserId(),cartItem.getProductId());
        if (!itemList.isEmpty()){
            CartItem item = itemList.get(0);
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            cartItem = item;
            //修改订单中的购买量
            cartItem.setTotalPrice(product.getPrice()*item.getQuantity());
            orderMapper.update(cartItem);
            return ResponseUtil.get(ResponseEnum.ORDER_UPDATE_SUCCESS,cartItem);
        }
        //判断商品库存是否充足
        if(product.getStoreNum() < cartItem.getQuantity()){
            return ResponseUtil.get(ResponseEnum.PRODUCT_NOT_ENOUGH);
        }else {
//            //修改商品库存
//            product.setStoreNum(product.getStoreNum() - cartItem.getQuantity());
//            productMapper.updateProduct(product);
            //设置订单信息
            cartItem.setCreateTime(new Date());
            cartItem.setTotalPrice(product.getPrice()*cartItem.getQuantity());
            cartItem.setUserId(UserContext.getUserId());
            //执行添加订单
            int result = orderMapper.addOrder(cartItem);
            if (result > 0){
                return ResponseUtil.get(ResponseEnum.ORDER_ADD_SUCCESS,cartItem);
            }
            return ResponseUtil.get(ResponseEnum.ORDER_ADD_FAIL);
        }
    }

    /**
     * 查询购物车商品列表
     * 
     * <p>根据当前登录用户ID查询其购物车中的所有商品。</p>
     * 
     * @return ResponseUtil 包含购物车商品列表的查询结果
     * @throws ParseException 用户ID解析异常
     */
    @Override
    public ResponseUtil getCartItems() throws ParseException {
        List<CartItem> cartItemList = orderMapper.queryByUserId(UserContext.getUserId());
        if (!cartItemList.isEmpty()){
            return ResponseUtil.get(ResponseEnum.ORDER_QUERY_SUCCESS,cartItemList);
        }else {
            return ResponseUtil.get(ResponseEnum.ORDER_QUERY_FAIL);
        }
    }
}
