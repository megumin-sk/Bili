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
public class Product implements Serializable {
    private Integer id;
    private String name;
    private Double price;
    private String description;
    private String imgUrl;
    private Integer storeNum;
    private String publisher;
    private String category;
}
