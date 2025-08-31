package com.bilibili.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.bilibili.entity.User;
import com.bilibili.mapper.UserMapper;
import com.bilibili.service.UserService;
import com.bilibili.utils.*;
import com.nimbusds.jose.JOSEException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;

/**
 * 用户服务实现类
 * 
 * <p>该类实现了用户相关的业务逻辑，包括：</p>
 * <ul>
 *   <li>用户登录认证</li>
 *   <li>用户信息查询</li>
 *   <li>用户信息更新</li>
 *   <li>用户注册</li>
 * </ul>
 * 
 * <p>使用JWT进行身份认证，Redis缓存JWT密钥，确保安全性。</p>
 * 
 * @author bilibili
 * @version 1.0
 * @since 2024
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    
    /**
     * 用户数据访问层接口
     */
    @Resource
    private UserMapper userMapper;
    
    /**
     * Redis模板，用于缓存JWT相关数据
     */
    @Resource
    private RedisTemplate redisTemplateInit;
    
    /**
     * 用户登录认证
     * 
     * <p>登录流程：</p>
     * <ol>
     *   <li>根据用户名查询用户信息</li>
     *   <li>验证用户密码（MD5加密比对）</li>
     *   <li>生成JWT token</li>
     *   <li>将JWT密钥存储到Redis</li>
     *   <li>返回用户信息和token</li>
     * </ol>
     * 
     * @param user 包含用户名和密码的用户对象
     * @return ResponseUtil 登录结果响应
     * @throws JOSEException JWT生成异常
     */
    public ResponseUtil login(User user) throws JOSEException {
        // 根据用户名查询用户信息
        User user1 = userMapper.login(user.getName());
        
        // 检查用户是否存在
        if (user1.getPassword() == null) {
            return ResponseUtil.get(ResponseEnum.USER_NOT_EXIST);
        } else {
            // 验证密码是否正确（MD5加密比对）
            if (SecureUtil.md5(user.getPassword()).equals(user1.getPassword())) {
                // 登录成功，清除密码信息
                user1.setPassword(null);
                
                // 将JWT密钥存储到Redis，用于后续验证
                redisTemplateInit.opsForValue().set(
                    ConstantUtil.REDIS_JWT_KEY, 
                    SecureUtil.md5(user.getPassword())
                );
                
                // 构建JWT payload数据
                HashMap<String, Object> map = new HashMap<>();
                map.put("username", user.getName());
                map.put("id", user1.getId());
                map.put("imgUrl", user1.getImgUrl());
                
                // 生成JWT token
                String token = JwtUtil.getToken(map);
                map.put("token", token);
                
                // 返回登录成功响应
                return ResponseUtil.get(ResponseEnum.USER_LOGIN_SUCCESS, map);
            } else {
                // 密码错误
                return ResponseUtil.get(ResponseEnum.USER_LOGIN_FAIL);
            }
        }
    }

    /**
     * 根据用户ID查询用户信息
     * 
     * <p>通过JWT token从UserContext获取当前登录用户ID，
     * 然后查询并返回用户详细信息。</p>
     * 
     * @param id 用户ID（参数未使用，实际从JWT token获取）
     * @return ResponseUtil 用户信息查询结果
     * @throws ParseException JWT解析异常
     */
    @Override
    public ResponseUtil queryUserById(Integer id) throws ParseException {
        // 从JWT token中获取当前登录用户ID
        Integer userId = UserContext.getUserId();
        
        // 根据用户ID查询用户信息
        User user = userMapper.queryUserById(userId);
        
        // 返回查询结果
        return ResponseUtil.get(ResponseEnum.USER_EXIST, user);
    }

    /**
     * 更新用户信息
     * 
     * <p>更新用户的个人信息，如昵称、头像等。</p>
     * 
     * @param user 包含更新信息的用户对象
     * @return ResponseUtil 更新结果响应
     */
    @Override
    public ResponseUtil updateUser(User user) {
        // 执行用户信息更新
        userMapper.updateUser(user);
        
        // 返回更新成功响应
        return ResponseUtil.get(ResponseEnum.USER_UPDATE_SUCCESS);
    }

    /**
     * 用户注册
     * 
     * <p>注册流程：</p>
     * <ol>
     *   <li>对用户密码进行MD5加密</li>
     *   <li>调用数据访问层保存用户信息</li>
     *   <li>返回注册结果</li>
     * </ol>
     * 
     * @param user 包含注册信息的用户对象
     * @return ResponseUtil 注册结果响应
     */
    @Override
    public ResponseUtil register(User user) {
        // 对用户密码进行MD5加密存储
        user.setPassword(SecureUtil.md5(user.getPassword()));
        
        // 执行用户注册，返回影响行数
        Integer register = userMapper.register(user);
        
        // 判断注册是否成功
        if (register == 1) {
            // 注册成功，返回用户信息
            return ResponseUtil.get(ResponseEnum.USER_REGISTER_SUCCESS, user);
        } else {
            // 注册失败
            return ResponseUtil.get(ResponseEnum.USER_REGISTER_FAIL);
        }
    }
}
