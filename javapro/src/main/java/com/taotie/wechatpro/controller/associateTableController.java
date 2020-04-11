package com.taotie.wechatpro.controller;

import com.taotie.wechatpro.dao.associateTable.*;
import com.taotie.wechatpro.service.impl.CardServiceImpl;
import com.taotie.wechatpro.service.impl.DiscussServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 创建时间: 2020/4/8 16:31
 * 文件备注:
 * 编写人员:
 */

@Controller
@RequestMapping("/v1")
public class associateTableController {
    @Autowired
    CardLableDao cardLableDao;
    @Autowired
    CardUserLikeDao cardUserLikeDao;
    @Autowired
    DiscussUserLikeDao discussUserLikeDao;
    @Autowired
    ResLableDao resLableDao;
    @Autowired
    UserLableDao userLableDao;
    @Autowired
    UserRestaurantDao userRestaurantDao;
    @Autowired
    DiscussServiceImpl discussService;

    @Autowired
    CardServiceImpl cardservice;

    //全部注释掉了，弃用
    //表达大致意思，后期具体再改
//    @ResponseBody
//    @RequestMapping(value = "/insertcardlable",method = RequestMethod.POST)
//    public void insertCardLable(/**大致意思是收到前台的json，不知道怎么表述*/){
//        //从json拿出CardLable的内容
//        //分成cardid和lableid分别作为参数存入cardlable表
//        cardLableDao.insert("","");
//    }


    @ResponseBody
    @RequestMapping(value = "/upDisUserLike",method = RequestMethod.GET)
    public void upDisLike(@RequestBody String str){
        discussService.upDisLike(str);
    }


    @ResponseBody
    @RequestMapping(value = "/upCardUserLike",method = RequestMethod.GET)
    public void upCardLike(@RequestBody String str){
        cardservice.upCardLike(str);
    }





}
