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
import java.util.UUID;

/**
 * 创建时间: 2020/4/8 19:42
 * 文件备注: 写了几个有关card_lable表和v_card_user_discuss视图的发送数据的接口给前端提取数据用
 * 编写人员: 杨伯益
 */

@Controller
@ResponseBody
@RequestMapping("/v1")
public class Controller2 {

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    CardLableDao cardLableDao;

    @Autowired
    VCardUserDiscussDao vCardUserDiscussDao;

    //根据cardId取cardlable表的信息
    @RequestMapping("/cardlable1/{id}")
    @ResponseBody
    public Object getCardLable1(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        //首先尝试从redis取出
        CardLable cardLable = (CardLable) redisTemplate.opsForValue().get("cardLable_cardId:"+id);
        if(cardLable==null){
            cardLable = cardLableDao.selectByCardId(id);
            redisTemplate.opsForValue().set("cardLable_cardId:"+id,cardLable);
        }
        return cardLable;
    }

    //根据userId取cardlable表的信息
    @RequestMapping("/cardlable2/{id}")
    @ResponseBody
    public Object getCardLable2(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        CardLable cardLable = (CardLable) redisTemplate.opsForValue().get("cardLable_userId:"+id);
        if(cardLable==null){
            cardLable = cardLableDao.selectByCardId(id);
            redisTemplate.opsForValue().set("cardLable_userId:"+id,cardLable);
        }
        return cardLable;
    }

    //根据cardId取vcarduserdisucuss视图的信息
    @RequestMapping("/vcarduserdisucuss1/{id}")
    @ResponseBody
    public Object getVCardUserDisucuss(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        List<VCardUserDiscuss> vCardUserDiscussList = (List<VCardUserDiscuss>) redisTemplate.opsForValue().get("vCardUserDiscuss_cardId:"+id);
        if(vCardUserDiscussList==null){
            vCardUserDiscussList = vCardUserDiscussDao.selectByCardId(id);
            redisTemplate.opsForValue().set("vCardUserDiscuss_cardId:"+id,vCardUserDiscussList);
        }
        return vCardUserDiscussList;

    }


    //根据userId取vcarduserdisucuss视图的信息
    @RequestMapping("/vcarduserdisucuss2/{id}")
    @ResponseBody
    public Object getVCardUserDisucuss2(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        List<VCardUserDiscuss> vCardUserDiscussList = (List<VCardUserDiscuss>) redisTemplate.opsForValue().get("vCardUserDiscussList_userId:"+id);
        if(vCardUserDiscussList==null){
            vCardUserDiscussList = vCardUserDiscussDao.selectByUserId(id);
            redisTemplate.opsForValue().set("vCardUserDiscussList_userId:"+id,vCardUserDiscussList);
        }
        return vCardUserDiscussList;
    }


}
