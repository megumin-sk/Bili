package com.bilibili.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {
    private Integer orderId;
    private Integer userId;
    private String productName;
    private Double price;
    private Date createTime;
    private Integer status;
    private Integer buyNum;
    private Integer productId;
}
