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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    User user;

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
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        String userName = JSON.parseObject(str).get("userName").toString();
        user = (User) redisTemplate.opsForValue().get("User_userId:"+userId);
        user.setUserName(userName);
        //完成user表的更新
        userDao.updateUserNameByUserId(userName,userId);
        List<UserLable> userLables = new ArrayList<UserLable>();
        //完成userlable表的插入
        for (int i = 1; i <= 3; i++){
            Integer lableId = Integer.parseInt(JSON.parseObject(str).get("lableId"+i).toString());
            userLable.setLableId(lableId);
            userLable.setUserId(userId);
            userLableDao.insertUserLable(lableId,userId);
            userLables.add(userLable);
        }

        //redisTemplate.opsForValue().set("User_userId:"+userId,user,1, TimeUnit.DAYS);
        //redisTemplate.opsForValue().set("UserLable_userId:"+userId,userLables,1, TimeUnit.DAYS);

        redisTemplate.opsForValue().set("User_userId:"+userId,userDao.selectByuserId(userId),1,TimeUnit.DAYS);
        redisTemplate.opsForValue().set("UserLable_userId:"+userId,userLableDao.selectByuserId(userId),1,TimeUnit.DAYS);



    }


    //用户收藏餐馆的上传
    public void upUserLikeRes(String str){
        //需要手动new，没有写工厂类太麻烦了
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        UserRestaurant userRestaurant = new UserRestaurant();
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        Integer resId = Integer.parseInt(JSON.parseObject(str).get("resId").toString());
        userRestaurant.setResId(resId);
        userRestaurant.setUserId(userId);
        userRestaurantDao.insert(userRestaurant);


        //这种多对多需要list形式
        //redisTemplate.opsForList().leftPush("UserRestaurant_userId:"+userId,userRestaurant);
        //redisTemplate.opsForList().leftPush("UserRestaurant_resId:"+resId,userRestaurant);
        //redisTemplate.expire("UserRestaurant_userId:"+userId,1, TimeUnit.DAYS);
        //redisTemplate.expire("UserRestaurant_resId:"+resId,1, TimeUnit.DAYS);

        redisTemplate.opsForValue().set("UserRestaurant_userId:"+userId,userRestaurantDao.selectByuserId(userId),1,TimeUnit.DAYS);
        redisTemplate.opsForValue().set("UserRestaurant_resId:"+resId,userRestaurantDao.selectByresId(resId),1,TimeUnit.DAYS);
    }


    public void upUserHateRes(String str){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        Integer resId = Integer.parseInt(JSON.parseObject(str).get("resId").toString());
        UserRestaurant userRestaurant = new UserRestaurant();
        userRestaurant.setUserId(userId);
        userRestaurant.setResId(resId);
        userRestaurantDao.deletByuserIdresId(userId,resId);

        //redisTemplate.opsForList().remove("UserRestaurant_userId:"+userId,1,userRestaurant);
        //redisTemplate.opsForList().remove("UserRestaurant_resId:"+resId,1,userRestaurant);

        redisTemplate.opsForValue().set("UserRestaurant_userId:"+userId,userRestaurantDao.selectByuserId(userId),1,TimeUnit.DAYS);
        redisTemplate.opsForValue().set("UserRestaurant_resId:"+resId,userRestaurantDao.selectByresId(resId),1,TimeUnit.DAYS);
    }



}
