package com.bilibili.controller;

import com.bilibili.entity.User;
import com.bilibili.service.UserService;
import com.bilibili.utils.ResponseUtil;
import com.bilibili.utils.UserContext;
import com.nimbusds.jose.JOSEException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 用户控制器
 * 
 * <p>处理用户相关的HTTP请求，包括用户登录、查询用户信息、修改用户信息和用户注册。</p>
 * 
 * @author wang
 * @version 0.0.1
 * @since 2025-04-05
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    /** 用户服务层接口 */
    @Resource
    private UserService userService;

    /**
     * 用户登录
     * 
     * <p>处理POST请求，调用用户服务层的login方法进行用户登录。</p>
     * 
     * @param user 用户信息
     * @return ResponseUtil 包含登录结果的响应工具类
     * @throws JOSEException JWT处理异常
     */
    @PostMapping("/login")
    public ResponseUtil login(@RequestBody User user) throws JOSEException {
        return userService.login(user);
    }

    /**
     * 查询用户信息
     * 
     * <p>处理GET请求，调用用户服务层的queryUserById方法查询用户信息。</p>
     * 
     * @return ResponseUtil 包含用户信息的响应工具类
     * @throws ParseException 用户ID解析异常
     */
    @GetMapping("/queryUserById")
    public ResponseUtil queryUserById() throws ParseException {
        Integer userId = UserContext.getUserId();
        return userService.queryUserById(userId);
    }

    /**
     * 修改用户信息
     * 
     * <p>处理POST请求，调用用户服务层的updateUser方法修改用户信息。</p>
     * 
     * @param user 用户信息
     * @return ResponseUtil 包含修改结果的响应工具类
     * @throws ParseException 用户ID解析异常
     */
    @PostMapping("/updateUser")
    public ResponseUtil updateUser(@RequestBody User user) throws ParseException {
        return userService.updateUser(user);
    }

    /**
     * 用户注册
     * 
     * <p>处理POST请求，调用用户服务层的register方法进行用户注册。</p>
     * 
     * @param user 用户信息
     * @return ResponseUtil 包含注册结果的响应工具类
     */
    @PostMapping("/register")
    public ResponseUtil register(@RequestBody User user) {
        return userService.register(user);
    }

}

