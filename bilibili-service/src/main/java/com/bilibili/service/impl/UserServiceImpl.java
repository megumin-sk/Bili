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

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate redisTemplateInit;
    public ResponseUtil login(User user) throws JOSEException {
        User user1 = userMapper.login(user.getName());
        if (user1.getPassword() == null){
            return ResponseUtil.get(ResponseEnum.USER_NOT_EXIST);
        }else {
            if (SecureUtil.md5(user.getPassword()).equals(user1.getPassword())){
                user1.setPassword(null);
                redisTemplateInit.opsForValue().set(ConstantUtil.REDIS_JWT_KEY,SecureUtil.md5(user.getPassword()));
                HashMap<String,Object> map = new HashMap<>();
                map.put("username",user.getName());
                map.put("id",user1.getId());
                String token = JwtUtil.getToken(map);
                return ResponseUtil.get(ResponseEnum.USER_LOGIN_SUCCESS,token);
            }else {
                return ResponseUtil.get(ResponseEnum.USER_LOGIN_FAIL);
            }
        }
    }

    @Override
    public ResponseUtil queryUserById(Integer id) throws ParseException {
        Integer userId = UserContext.getUserId();
        User user = userMapper.queryUserById(userId);
        return ResponseUtil.get(ResponseEnum.USER_EXIST,user);
    }

    @Override
    public ResponseUtil updateUser(User user) {
        userMapper.updateUser(user);
        return ResponseUtil.get(ResponseEnum.USER_UPDATE_SUCCESS);
    }

    @Override
    public ResponseUtil register(User user) {
        user.setPassword(SecureUtil.md5(user.getPassword()));
        Integer register = userMapper.register(user);
        if (register == 1){
            return ResponseUtil.get(ResponseEnum.USER_REGISTER_SUCCESS,user);
        }else {
            return ResponseUtil.get(ResponseEnum.USER_REGISTER_FAIL);
        }
    }
}
