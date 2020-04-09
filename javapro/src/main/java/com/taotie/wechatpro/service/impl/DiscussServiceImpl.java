package com.taotie.wechatpro.service.impl;

import com.alibaba.fastjson.JSON;
import com.taotie.wechatpro.dao.DiscussDao;
import com.taotie.wechatpro.dao.associateTable.DiscussUserLikeDao;
import com.taotie.wechatpro.pojo.Discuss;
import com.taotie.wechatpro.pojo.association.DiscussUserLike;
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
    DiscussUserLikeDao disuserlikeDao;

    @Autowired
    DiscussUserLike disuserlike;

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


    public void upDisLike(String str){
        Integer disID = Integer.parseInt(JSON.parseObject(str).get("disId").toString());
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        disuserlike.setDiscussId(disID);
        disuserlike.setUserId(userId);
        disuserlikeDao.insert(disuserlike);
    }

    public void upDis(String str){
        Integer cardId = Integer.parseInt(JSON.parseObject(str).get("cardId").toString());
        String disComment = JSON.parseObject(str).get("disContent").toString();
        Integer parentId = Integer.parseInt(JSON.parseObject(str).get("parentId").toString());
        String disThread = JSON.parseObject(str).get("parentThread").toString();
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("disuserId").toString());
        Discuss dis = new Discuss();
        dis.setCardId(cardId);
        dis.setDisComment(disComment);
        dis.setParentId(parentId);
        dis.setDisThread(disThread);
        dis.setDisUserId(userId);
        disdao.insert(dis);
    }
}
