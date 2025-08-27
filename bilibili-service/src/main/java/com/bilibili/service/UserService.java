package com.bilibili.service;

import com.bilibili.entity.User;
import com.bilibili.utils.ResponseUtil;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface UserService {
    ResponseUtil login(User user) throws JOSEException;
    ResponseUtil queryUserById(Integer id) throws ParseException;

    ResponseUtil updateUser(User user);
}
