package com.taotie.wechatpro.controller;

import com.taotie.wechatpro.dao.associateTable.CardLableDao;
import com.taotie.wechatpro.dao.view.VCardUserDiscussDao;
import com.taotie.wechatpro.pojo.User;
import com.taotie.wechatpro.pojo.association.CardLable;
import com.taotie.wechatpro.pojo.view.VCardUserDiscuss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 创建时间: 2020/4/8 19:42
 * 文件备注: 仅仅用作测试，无其他用途
 * 编写人员:
 */

@Controller
@ResponseBody//@ResponseBody的作用其实是将java对象转为json格式的数据
@RequestMapping("/v1")
public class testController {

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    CardLableDao cardLableDao;

    @Autowired
    VCardUserDiscussDao vCardUserDiscussDao;

    @RequestMapping("/test1/{id}")
    @ResponseBody
    public Object getCardLable(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        //首先尝试从redis取出
        CardLable cardLable = (CardLable) redisTemplate.opsForValue().get("cardLable_cardId:"+id);
        if(cardLable==null){
            //没有就从数据库取，然后存入redis
            //key为id，value为user对象
            cardLable = cardLableDao.selectByCardId(id);
            redisTemplate.opsForValue().set("cardLable_cardId:"+id,cardLable);
        }
        return cardLable;
    }

    @RequestMapping("/test2/{id}")
    @ResponseBody
    public Object test2(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        //首先尝试从redis取出
        List<VCardUserDiscuss> vCardUserDiscussList = (List<VCardUserDiscuss>) redisTemplate.opsForValue().get("vCardUserDiscuss_cardId:"+id);
        if(vCardUserDiscussList==null){
            //没有就从数据库取，然后存入redis
            //key为id，value为user对象
            vCardUserDiscussList = vCardUserDiscussDao.selectByCardId(id);
            redisTemplate.opsForValue().set("vCardUserDiscuss_cardId:"+id,vCardUserDiscussList);
        }
        return vCardUserDiscussList;
    }

    @RequestMapping("/test3/{id}")
    @ResponseBody
    public Object test3(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        //首先尝试从redis取出
        List<VCardUserDiscuss> vCardUserDiscussList = (List<VCardUserDiscuss>) redisTemplate.opsForValue().get("vCardUserDiscussList_userId:"+id);
        if(vCardUserDiscussList==null){
            //没有就从数据库取，然后存入redis
            //key为id，value为user对象
            vCardUserDiscussList = vCardUserDiscussDao.selectByUserId(id);
            redisTemplate.opsForValue().set("vCardUserDiscussList_userId:"+id,vCardUserDiscussList);
        }
        return vCardUserDiscussList;
    }
}
