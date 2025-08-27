package com.bilibili.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private String password;
    //0是女 1是男 2是不透露性别
    private Integer sex;
    private Date birthday;
    private String school;
    private String introduction;
    private LocalDate joinTime;
    private LocalDate lastLoginTime;
    private String lastLoginIp;
    private Integer status;
    private String notice;
    private Integer coinNum;
}
