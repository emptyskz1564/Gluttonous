package com.taotie.wechatpro.service.impl;

import com.alibaba.fastjson.JSON;
import com.taotie.wechatpro.dao.UserDao;
import com.taotie.wechatpro.dao.associateTable.UserLableDao;
import com.taotie.wechatpro.dao.associateTable.UserRestaurantDao;
import com.taotie.wechatpro.pojo.User;
import com.taotie.wechatpro.pojo.association.UserLable;
import com.taotie.wechatpro.pojo.association.UserRestaurant;
import com.taotie.wechatpro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建时间: 2020/4/6 15:24
 * 文件备注:
 * 编写人员:
 */

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    UserLableDao userLableDao;

    @Autowired
    UserRestaurantDao userRestaurantDao;

    @Autowired
    UserLable userLable;

    @Autowired
    UserRestaurant userRestaurant;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public List<User> getUserList() {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        List<User> list = (List<User>)redisTemplate.opsForValue().get("allUser");
        if(list==null){
            list=userDao.selectList(null);
            redisTemplate.opsForValue().set("allUser",list);
        }

        return list;

    }


    //用户更改昵称和标签的上传
    public void upchangeUser(String str){
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        String userName = JSON.parseObject(str).get("userName").toString();
        //完成user表的更新
        userDao.updateUserNameByUserId(userName,userId);
        //完成userlable表的插入
        for (int i = 1; i <= 3; i++){
            Integer lableId = Integer.parseInt(JSON.parseObject(str).get("lableId"+i).toString());
            userLable.setLableId(lableId);
            userLable.setUserId(userId);
            userLableDao.insertUserLable(lableId,userId);
        }
    }


    //用户收藏餐馆的上传
    public void upUserLikeRes(String str){
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        Integer resId = Integer.parseInt(JSON.parseObject(str).get("resId").toString());
        userRestaurant.setResId(resId);
        userRestaurant.setUserId(userId);
        userRestaurantDao.insert(userRestaurant);
    }



}
