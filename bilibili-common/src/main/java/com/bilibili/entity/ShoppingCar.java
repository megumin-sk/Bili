package com.bilibili.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCar implements Serializable {
    private String carId;
    private Integer userId;
    private Double totalPrice;
    private Integer status;
    private Integer addressId;
    private List<Integer> ids;
}
