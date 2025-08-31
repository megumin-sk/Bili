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
    USER_UPDATE_FAIL(405, "用户更新失败"),
    USER_REGISTER_SUCCESS(202, "用户注册成功"),
    USER_REGISTER_FAIL(406, "用户注册失败"),
    // 商品模块
    PRODUCT_QUERY_SUCCESS(200, "查询全部成功"),
    PRODUCT_QUERY_FAIL(400, "查询全部失败"),
    PRODUCT_ADD_SUCCESS(201, "添加商品成功"),
    PRODUCT_ADD_FAIL(400, "添加商品失败"),
    PRODUCT_DETAIL_SUCCESS(200, "查询商品详情成功"),
    PRODUCT_DETAIL_FAIL(400, "查询商品详情失败"),
    // 订单模块
    PRODUCT_NOT_ENOUGH(400, "商品库存不足"),
    ORDER_ADD_SUCCESS(201, "添加订单成功"),
    ORDER_ADD_FAIL(400, "添加订单失败"),
    ORDER_QUERY_SUCCESS(200, "查询订单成功"),
    ORDER_QUERY_FAIL(400, "查询订单失败"),
    ORDER_UPDATE_SUCCESS(201, "更新订单成功"),
    ORDER_UPDATE_FAIL(400, "更新订单失败"),
    ORDER_DELETE_SUCCESS(201, "删除订单成功"),
    //地址模块
    ADDRESS_QUERY_SUCCESS(201, "查询全部地址成功"),
    ADDRESS_QUERY_FAIL(400, "地址为空");

    private Integer code;
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
