package com.bilibili.utils;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    USER_LOGIN_SUCCESS(200, "登录成功"),
    USER_LOGIN_FAIL(400, "登录失败,密码错误"),
    USER_NOT_EXIST(404, "用户不存在"),
    USER_EXIST(403, "用户查询成功"),
    TOKEN_IS_NULL(401, "token为空"),
    TOKEN_IS_ERROR(402, "token错误"),
    USER_UPDATE_SUCCESS(201, "用户更新成功"),
    USER_UPDATE_FAIL(405, "用户更新失败");

    private Integer code;
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
