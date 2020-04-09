package com.taotie.wechatpro.controller;

import com.taotie.wechatpro.service.impl.DiscussServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1")
public class DiscussController {

    @Autowired
    DiscussServiceImpl discussService;

    /**
     * 得到评论的上传json
     * @param str
     */
    @ResponseBody
    @RequestMapping(value = "/upDis",method = RequestMethod.GET)
    public void upDis(@RequestBody String str){
        discussService.upDis(str);
    }
}
