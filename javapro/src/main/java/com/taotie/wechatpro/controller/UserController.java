package com.taotie.wechatpro.controller;


import com.taotie.wechatpro.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @ResponseBody
    @RequestMapping(value = "/upchangeUser",method = RequestMethod.GET)
    public void upchangeUser(@RequestBody String str){
        userService.upchangeUser(str);
    }

    @ResponseBody
    @RequestMapping(value = "/upUserLikeRes",method = RequestMethod.GET)
    public void upUserLikeRes(@RequestBody String str){
        userService.upUserLikeRes(str);
    }
}
