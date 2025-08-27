package com.bilibili.mapper;

import com.bilibili.entity.User;

public interface UserMapper {
    User login(String name);
    User queryUserById(Integer id);
    Integer updateUser(User user);
}
