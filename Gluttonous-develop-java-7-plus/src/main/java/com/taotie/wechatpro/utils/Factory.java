package com.taotie.wechatpro.utils;

import com.taotie.wechatpro.pojo.association.UserRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * 创建时间: 2020/4/13 15:10
 * 文件备注:
 * 编写人员:
 */


@Service
public class Factory {

    @Autowired
    static UserRestaurant userRestaurant;

    @Bean
    public static UserRestaurant getuserrestaurant(){
        return userRestaurant;
    }
}
