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



    @ResponseBody
    @RequestMapping(value = "/upDisUserLike",method = RequestMethod.POST)
    public Integer upDisLike(@RequestParam@RequestBody String str){
        discussService.upDisLike(str);
        return 1;
    }


    @ResponseBody
    @RequestMapping(value = "/upCardUserLike",method = RequestMethod.POST)
    public Integer upCardLike(@RequestParam@RequestBody String str){
        cardservice.upCardLike(str);
        return 1;
    }





}
