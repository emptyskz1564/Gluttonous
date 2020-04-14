package com.taotie.wechatpro.controller;


import com.taotie.wechatpro.service.impl.ResServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/v1")
public class RestaurantController {

    @Autowired
    ResServiceImpl resService;

    @ResponseBody
    @RequestMapping(value = "/upRes1",method = RequestMethod.POST)
    public Integer upCard(@RequestParam(required = false,value = "files") MultipartFile multipartFile , @RequestParam@RequestBody String str) throws IOException {
        //完成正常json数据上传
        System.out.println(str);
        Integer integer = resService.upRes1(str,multipartFile);
        //如果遇到了不支持类型则返回-1,若成功则是cardId
        return integer;
    }


    @ResponseBody
    @RequestMapping(value = "/upRes2",method = RequestMethod.POST)
    public Integer upCard2(@RequestParam(required = false,value = "files")MultipartFile multipartFile ,@RequestParam@RequestBody String str) throws IOException {
        //完成正常json数据上传
        System.out.println(str);
        Integer integer = resService.upRes2(str,multipartFile);
        //如果遇到了不支持类型则返回-1,若成功则是cardId
        return integer;
    }
}
