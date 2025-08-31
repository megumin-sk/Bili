package com.bilibili.service.impl;

import com.bilibili.entity.Address;
import com.bilibili.mapper.AddressMapper;
import com.bilibili.service.AddressService;
import com.bilibili.utils.ResponseEnum;
import com.bilibili.utils.ResponseUtil;
import com.bilibili.utils.UserContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@Service("addressService")
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;
    @Override
    public ResponseUtil queryAll() throws ParseException {
        Integer userId = UserContext.getUserId();
        List<Address> list = addressMapper.queryAll(userId);
        if (!list.isEmpty()){
            return ResponseUtil.get(ResponseEnum.ADDRESS_QUERY_SUCCESS,list);
        }else {
            return ResponseUtil.get(ResponseEnum.ADDRESS_QUERY_FAIL);
        }
    }
}
