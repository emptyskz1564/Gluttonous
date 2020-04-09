package com.taotie.wechatpro.service.impl;

import com.alibaba.fastjson.JSON;
import com.taotie.wechatpro.dao.associateTable.CardUserLikeDao;
import com.taotie.wechatpro.pojo.Discuss;
import com.taotie.wechatpro.pojo.association.CardUserLike;
import com.taotie.wechatpro.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CardServiceImpl")
public class CardServiceImpl implements CardService {

    @Autowired
    CardUserLike carduserlike;

    @Autowired
    CardUserLikeDao carduserlikeDao;

    @Override
    public void upCardLike(String str) {
        Integer cardId = Integer.parseInt(JSON.parseObject(str).get("cardId").toString());
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        carduserlike.setCardId(cardId);
        carduserlike.setUserId(userId);
        carduserlikeDao.insert(carduserlike);
    }
}
