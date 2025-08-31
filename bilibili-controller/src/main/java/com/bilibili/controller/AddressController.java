package com.bilibili.controller;

import com.bilibili.service.AddressService;
import com.bilibili.utils.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 地址控制器类
 * 
 * <p>该类负责处理与用户地址相关的HTTP请求，包括：</p>
 * <ul>
 *   <li>查询用户所有地址信息</li>
 * </ul>
 * 
 * <p>通过调用AddressService实现具体的业务逻辑。</p>
 * 
 * @author bilibili
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    /**
     * 地址服务层接口
     */
    @Resource
    private AddressService addressService;

    /**
     * 查询用户所有地址信息
     * 
     * <p>通过JWT token从UserContext获取当前登录用户ID，
     * 然后查询并返回该用户的所有地址信息。</p>
     * 
     * @return ResponseUtil 包含地址列表的查询结果
     * @throws ParseException 用户ID解析异常
     */
    @GetMapping("/queryAll")
    public ResponseUtil queryAll() throws ParseException {
        return addressService.queryAll();
    }
}
