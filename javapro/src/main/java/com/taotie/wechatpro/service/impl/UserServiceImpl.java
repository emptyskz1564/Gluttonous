package com.taotie.wechatpro.service.impl;

import com.taotie.wechatpro.dao.UserDao;
import com.taotie.wechatpro.pojo.User;
import com.taotie.wechatpro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

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



}
