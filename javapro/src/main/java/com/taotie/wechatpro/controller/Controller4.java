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
    @RequestMapping(value = "/userresbyuserid/{id}",method = RequestMethod.GET)
    private Object showUserLikeRes1(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        List<UserRestaurant> userRestaurantList = (List<UserRestaurant>) redisTemplate.opsForValue().get("UserRestaurant_userId:"+id);
        if(userRestaurantList==null){
            userRestaurantList = userRestaurantDao.selectByuserId(Integer.valueOf(id));
            redisTemplate.opsForValue().set("UserRestaurant_userId:"+id,userRestaurantList);
        }
        return userRestaurantList;
    }

    //根据resId获取用户餐厅收藏
    @ResponseBody
    @RequestMapping(value = "/userresbyresid/{id}",method = RequestMethod.GET)
    private Object showUserLikeRes2(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        List<UserRestaurant> userRestaurantList = (List<UserRestaurant>) redisTemplate.opsForValue().get("UserRestaurant_resId:"+id);
        if(userRestaurantList==null){
            userRestaurantList = userRestaurantDao.selectByuserId(Integer.valueOf(id));
            redisTemplate.opsForValue().set("UserRestaurant_resId:"+id,userRestaurantList);
        }
        return userRestaurantList;
    }



    //根据disId收取视图，前端需要哪些就取，根据key
    @ResponseBody
    @RequestMapping(value = "/vcarduserdiscuss/{id}",method = RequestMethod.GET)
    private Object vcarduserdiscuss(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        VCardUserDiscuss vCardUserDiscuss = (VCardUserDiscuss) redisTemplate.opsForValue().get("VCardUserDiscuss_disId:"+id);
        if(vCardUserDiscuss==null){
            vCardUserDiscuss = vCardUserDiscussDao.selectByDisId(Integer.valueOf(id));
            redisTemplate.opsForValue().set("VCardUserDiscuss_disId:"+id,vCardUserDiscuss);
        }
        return vCardUserDiscuss;
    }








}
