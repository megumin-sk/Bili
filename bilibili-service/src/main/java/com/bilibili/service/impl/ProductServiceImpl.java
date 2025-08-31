package com.bilibili.service.impl;

import com.bilibili.entity.Product;
import com.bilibili.mapper.ProductMapper;
import com.bilibili.service.ProductService;
import com.bilibili.utils.ConstantUtil;
import com.bilibili.utils.ResponseEnum;
import com.bilibili.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品服务实现类
 * 
 * <p>该类实现了商品相关的业务逻辑，包括：</p>
 * <ul>
 *   <li>查询全部商品信息</li>
 *   <li>查询商品详情</li>
 *   <li>Redis缓存管理</li>
 * </ul>
 * 
 * <p>使用Redis缓存提高查询性能，减少数据库访问压力。
 * 实现了缓存优先的策略：先查缓存，缓存未命中时查询数据库并更新缓存。</p>
 * 
 * @author bilibili
 * @version 1.0
 * @since 2024
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {
    
    /**
     * 商品数据访问层接口
     */
    @Resource
    private ProductMapper productMapper;

    /**
     * Redis模板，用于商品数据缓存
     */
    @Resource
    private RedisTemplate redisTemplateInit;

    /**
     * 查询全部商品信息
     * 
     * <p>查询策略：</p>
     * <ol>
     *   <li>优先从Redis缓存中查询商品列表</li>
     *   <li>如果缓存命中，直接返回缓存数据</li>
     *   <li>如果缓存未命中，从数据库查询并更新缓存</li>
     *   <li>使用Redis Hash结构存储商品数据，提高查询效率</li>
     * </ol>
     * 
     * <p>缓存键：{@link ConstantUtil#REDIS_ALL_PRODUCT}</p>
     * 
     * @return ResponseUtil 包含商品列表的查询结果
     */
    @Override
    public ResponseUtil queryAll() {
        // 初始化商品列表
        List<Product> productList = new ArrayList<>();
        
        // 从Redis缓存中查询全部商品
        Map map = redisTemplateInit.opsForHash().entries(ConstantUtil.REDIS_ALL_PRODUCT);
        
        // 检查缓存是否命中
        if (!map.isEmpty()) {
            // 缓存命中，将缓存数据添加到商品列表
            productList.addAll(map.values());
            return ResponseUtil.get(ResponseEnum.PRODUCT_QUERY_SUCCESS, productList);
        } else {
            // 缓存未命中，从数据库查询商品信息
            productList = productMapper.queryAll();
            
            // 将商品列表转换为Map，用于Redis缓存
            // 键格式：REDIS_PRODUCT_DETAIL + 商品ID
            // 值：商品对象
            Map<String, Product> productMap = productList.stream().collect(Collectors.toMap(
                    e -> ConstantUtil.REDIS_PRODUCT_DETAIL + e.getId(),
                    e -> e
            ));
            
            // 将查询结果存储到Redis缓存中
            redisTemplateInit.opsForHash().putAll(ConstantUtil.REDIS_ALL_PRODUCT, productMap);
            
            // 返回查询结果
            return ResponseUtil.get(ResponseEnum.PRODUCT_QUERY_SUCCESS, productList);
        }
    }

    /**
     * 根据商品ID查询商品详情
     * 
     * <p>直接查询数据库获取商品详情，不涉及缓存操作。
     * 适用于需要实时商品信息的场景。</p>
     * 
     * @param id 商品ID
     * @return ResponseUtil 包含商品详情的查询结果
     */
    @Override
    public ResponseUtil queryById(Integer id) {
        // 根据商品ID查询商品详情
        Product product = productMapper.queryById(id);
        
        // 检查商品是否存在
        if (product != null) {
            // 商品存在，返回成功响应
            return ResponseUtil.get(ResponseEnum.PRODUCT_DETAIL_SUCCESS, product);
        } else {
            // 商品不存在，返回失败响应
            return ResponseUtil.get(ResponseEnum.PRODUCT_DETAIL_FAIL);
        }
    }
}
