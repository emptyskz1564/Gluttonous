package com.taotie.wechatpro.controller.get;

import com.taotie.wechatpro.dao.DiscussDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    //根据disId拿取倒序评论，越晚的评论在上面
    @RequestMapping("/discusses/desc/{dis_id}")
    @ResponseBody
    public Object getDisDesc(@PathVariable String dis_id){
        return discussDao.showDisDesc(Integer.valueOf(dis_id));


    }


}
