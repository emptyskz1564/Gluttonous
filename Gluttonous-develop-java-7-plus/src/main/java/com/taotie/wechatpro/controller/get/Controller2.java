package com.taotie.wechatpro.controller.get;

import com.taotie.wechatpro.dao.CardDao;
import com.taotie.wechatpro.dao.associateTable.CardLableDao;
import com.taotie.wechatpro.dao.view.VCardUserDiscussDao;
import com.taotie.wechatpro.pojo.Card;
import com.taotie.wechatpro.pojo.association.CardLable;
import com.taotie.wechatpro.pojo.someother.PicUrl;
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

import java.util.List;

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

    @Autowired
    CardDao cardDao;

//    @Autowired
//    PicUrl picUrl;

    //根据cardId取cardlable表的信息
    @RequestMapping("/cardlable/card/{id}")
    @ResponseBody
    public Object getCardLable1(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        //首先尝试从redis取出
        CardLable cardLable = (CardLable) redisTemplate.opsForValue().get("CardLable_cardId:"+id);
        if(cardLable==null){
            cardLable = cardLableDao.selectByCardId(id);
            redisTemplate.opsForValue().set("CardLable_cardId:"+id,cardLable);
        }
        return cardLable;
    }

    //根据userId取cardlable表的信息
    @RequestMapping("/cardlable/user/{id}")
    @ResponseBody
    public Object getCardLable2(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        CardLable cardLable = (CardLable) redisTemplate.opsForValue().get("CardLable_userId:"+id);
        if(cardLable==null){
            cardLable = cardLableDao.selectByCardId(id);
            redisTemplate.opsForValue().set("CardLable_userId:"+id,cardLable);
        }
        return cardLable;
    }

    //根据cardId取vcarduserdisucuss视图的信息
    @RequestMapping("/vcarduserdisucuss1/{id}")
    @ResponseBody
    public Object getVCardUserDisucuss(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        List<VCardUserDiscuss> vCardUserDiscussList = (List<VCardUserDiscuss>) redisTemplate.opsForValue().get("VCardUserDiscuss_cardId:"+id);
        if(vCardUserDiscussList==null){
            vCardUserDiscussList = vCardUserDiscussDao.selectByCardId(Integer.valueOf(id));
            redisTemplate.opsForValue().set("VCardUserDiscuss_cardId:"+id,vCardUserDiscussList);
        }
        return vCardUserDiscussList;

    }


    //根据userId取vcarduserdisucuss视图的信息
    @RequestMapping(value = "/vcarduserdisucuss2/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object getVCardUserDisucuss2(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        List<VCardUserDiscuss> vCardUserDiscussList = (List<VCardUserDiscuss>) redisTemplate.opsForValue().get("VCardUserDiscussList_userId:"+id);
        if(vCardUserDiscussList==null){
            vCardUserDiscussList = vCardUserDiscussDao.selectByUserId(Integer.valueOf(id));
            redisTemplate.opsForValue().set("VCardUserDiscussList_userId:"+id,vCardUserDiscussList);
        }
        return vCardUserDiscussList;
    }






}
