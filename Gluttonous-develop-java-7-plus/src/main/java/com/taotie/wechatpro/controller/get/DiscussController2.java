package com.taotie.wechatpro.controller.get;

import com.taotie.wechatpro.dao.DiscussDao;
import com.taotie.wechatpro.pojo.Discuss;
import com.taotie.wechatpro.pojo.association.UserRestaurant;
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
 * 创建时间: 2020/4/16 8:59
 * 文件备注: 接收评论
 * 编写人员: 杨伯益
 */


@Controller
@ResponseBody
@RequestMapping("/v1")
public class DiscussController2 {

    @Autowired
    DiscussDao discussDao;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    //根据cardId拿取倒序评论，越晚的评论在上面
    @RequestMapping("/discusses/desc/{id}")
    @ResponseBody
    public Object getDisDesc(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        List<Discuss> discussList = (List<Discuss>) redisTemplate.opsForValue().get("Discuss_cardId:"+id);
        if(discussList==null){
            discussList = discussDao.showDisDesc(Integer.valueOf(id));
            redisTemplate.opsForValue().set("Discuss_cardId:"+id,discussList);
        }
        return discussList;
    }




}
