package com.taotie.wechatpro.service.impl;

import com.taotie.wechatpro.dao.DiscussDao;
import com.taotie.wechatpro.pojo.Discuss;
import com.taotie.wechatpro.utils.pojo2json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DiscussServiceImpl")
public class DiscussServiceImpl {

    @Autowired
    DiscussDao disdao;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    pojo2json p2j = new pojo2json();

    public List<Discuss> showAllDis(Integer cardId) {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        List<Discuss> list = (List<Discuss>)redisTemplate.opsForValue().get("allDis_cardId:"+cardId);
        if (list == null) {
        list = disdao.showalldis(cardId);
            String jslist = p2j.toJson(list);
            redisTemplate.opsForValue().set("allDis_cardId:"+cardId, list);
        }

        return list;
    }
}
