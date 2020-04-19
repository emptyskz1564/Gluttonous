package com.taotie.wechatpro.controller.get;

import com.taotie.wechatpro.dao.view.VCardUserDiscussDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 创建时间: 2020/4/16 15:15
 * 文件备注: 后来新增的对于视图的接口
 * 编写人员: 杨伯益
 */

@Controller
@ResponseBody
@RequestMapping("/v1")
public class VController {

    @Autowired
    VCardUserDiscussDao vCardUserDiscussDao;

    @ResponseBody
    @RequestMapping(value = "/vcarduserdiscuss/card/{card_id}",method = RequestMethod.GET)
    public Object getvcarduserdiscuss(@PathVariable String card_id){

        return vCardUserDiscussDao.selectByCardId(Integer.valueOf(card_id));
    }
}
