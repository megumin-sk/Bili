package com.bilibili.mapper;

import com.bilibili.entity.Address;

import java.util.List;

public interface AddressMapper {
    List<Address> queryAll(Integer userId);
}
