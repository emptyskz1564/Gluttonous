package com.taotie.wechatpro.controller.get;

import com.taotie.wechatpro.dao.CardDao;
import com.taotie.wechatpro.dao.RestaurantDao;
import com.taotie.wechatpro.pojo.Card;
import com.taotie.wechatpro.pojo.CardUser;
import com.taotie.wechatpro.pojo.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 创建时间: 2020/4/10 18:40
 * 文件备注: 4/10晚上新增的几个接口，刘斯昊给的那几个
 * 编写人员: 杨伯益
 */

@Controller
@ResponseBody
@RequestMapping("/v1")
public class controller3 {


    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    CardDao cardDao;


    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    //获取所有restaurant表信息
    @ResponseBody
    @RequestMapping(value = "/restaurants",method = RequestMethod.GET)
    public Object getRestaurant(){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        List<Restaurant> restaurantList = (List<Restaurant>) redisTemplate.opsForValue().get("allRestaurant");
        if(restaurantList==null){
            restaurantList = restaurantDao.selectList(null);
            redisTemplate.opsForValue().set("allRestaurant",restaurantList);
        }
        return restaurantList;
    }

    //获取所有card表信息
    @ResponseBody
    @RequestMapping(value = "/cards",method = RequestMethod.GET)
    public Object getcard(){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        List<Card> cardList = (List<Card>) redisTemplate.opsForValue().get("allCard");
        if(cardList==null){
            cardList = cardDao.selectList(null);
            redisTemplate.opsForValue().set("allCard",cardList);
        }
        return cardList;
    }

    //获取所有carduser表信息(专供打卡列表)
    @ResponseBody
    @RequestMapping(value = "/cardusers",method = RequestMethod.GET)
    public Object getcarduser(){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        List<CardUser> cardList = (List<CardUser>) redisTemplate.opsForValue().get("allCardUser");
        if(cardList==null){
            cardList = cardDao.selectAllCardUser();
            redisTemplate.opsForValue().set("allCardUser",cardList);
        }
        return cardList;
    }



}
