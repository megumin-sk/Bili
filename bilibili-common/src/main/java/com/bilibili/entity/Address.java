package com.bilibili.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
    private Integer addressId;
    private String addressName;
    private String nickName;
    private Integer userId;
    private String comment;
}
