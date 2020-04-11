package com.taotie.wechatpro.controller;

import com.taotie.wechatpro.dao.associateTable.UserRestaurantDao;
import com.taotie.wechatpro.dao.view.VCardUserDiscussDao;
import com.taotie.wechatpro.pojo.Restaurant;
import com.taotie.wechatpro.pojo.association.UserRestaurant;
import com.taotie.wechatpro.pojo.view.VCardUserDiscuss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

/**
 * 创建时间: 2020/4/11 12:00
 * 文件备注: 许祁4/11早上给的刘斯昊发过来的几个接口写一下
 * 编写人员: 杨伯益
 */

@Controller
@ResponseBody
@RequestMapping("/v1")
public class Controller4 {


    @Autowired
    UserRestaurantDao userRestaurantDao;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    VCardUserDiscussDao vCardUserDiscussDao;




    //根据userId获取用户餐厅收藏
    @ResponseBody
    @RequestMapping(value = "/showuserlikeres/{id}",method = RequestMethod.GET)
    private Object showUserLikeRes(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        UserRestaurant userRestaurant = (UserRestaurant) redisTemplate.opsForValue().get("userRestaurant:"+id);
        if(userRestaurant==null){
            userRestaurant = userRestaurantDao.selectByUserId(Integer.valueOf(id));
            redisTemplate.opsForValue().set("userRestaurant:"+id,userRestaurant);
        }
        return userRestaurant;
    }


    //根据disId收取视图，前端需要哪些就取，根据key
    @ResponseBody
    @RequestMapping(value = "/vcarduserdiscuss/{id}",method = RequestMethod.GET)
    private Object vcarduserdiscuss(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        VCardUserDiscuss vCardUserDiscuss = (VCardUserDiscuss) redisTemplate.opsForValue().get("vCardUserDiscuss_disId:"+id);
        if(vCardUserDiscuss==null){
            vCardUserDiscuss = vCardUserDiscussDao.selectByDisId(Integer.valueOf(id));
            redisTemplate.opsForValue().set("vCardUserDiscuss_disId:"+id,vCardUserDiscuss);
        }
        return vCardUserDiscuss;
    }



}
