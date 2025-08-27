package com.bilibili.controller;

import com.bilibili.entity.User;
import com.bilibili.service.UserService;
import com.bilibili.utils.ResponseUtil;
import com.bilibili.utils.UserContext;
import com.nimbusds.jose.JOSEException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseUtil login(@RequestBody User user) throws JOSEException {
        return userService.login(user);
    }

    @GetMapping("/queryUserById")
    public ResponseUtil queryUserById() throws ParseException {
        Integer userId = UserContext.getUserId();
        return userService.queryUserById(userId);
    }

    @PostMapping("/updateUser")
    public ResponseUtil updateUser(@RequestBody User user) throws ParseException {
        return userService.updateUser(user);
    }
}

