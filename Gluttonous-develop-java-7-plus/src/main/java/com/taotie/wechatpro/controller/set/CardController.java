package com.taotie.wechatpro.controller.set;


import com.taotie.wechatpro.dao.CardDao;
import com.taotie.wechatpro.pojo.Card;
import com.taotie.wechatpro.pojo.someother.PicUrl;
import com.taotie.wechatpro.service.impl.CardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/v1")
public class CardController {
    @Autowired
    CardServiceImpl cardService;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    CardDao cardDao;


    //许祁写的这部分，支持图片视频一并上传到云上
    @ResponseBody
    @RequestMapping(value = "/card/first",method = RequestMethod.POST)
    public Integer upCard(@RequestParam(required = false,value = "files")MultipartFile[] multipartFiles ,@RequestParam@RequestBody String str) throws IOException {
        //完成正常json数据上传
        System.out.println(str);
        Integer integer = cardService.upCard1(str,multipartFiles);
        //如果遇到了不支持类型则返回-1,若成功则是cardId
        return integer;
    }


    @ResponseBody
    @RequestMapping(value = "/card/second",method = RequestMethod.POST)
    public Integer upCard2(@RequestParam(required = false,value = "files")MultipartFile multipartFile ,@RequestParam@RequestBody String str) throws IOException {
        //完成正常json数据上传
        System.out.println(str);
        Integer integer = cardService.upCard2(str,multipartFile);
        //如果遇到了不支持类型则返回-1,若成功则是cardId
        return integer;
    }


    //根据cardId可以提取相应的图片url，通过json发给前端
    @ResponseBody
    @RequestMapping(value = "/picurl/{id}",method = RequestMethod.GET)
    public Object getpicurl(@PathVariable String id){

        PicUrl picUrl = new PicUrl();

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Card card = (Card)redisTemplate.opsForValue().get("Card_cardId:"+id);
        if(card==null){
            card =cardDao.selectById(id);
            redisTemplate.opsForValue().set("Card_cardId:"+id,card);
        }
        String url[] = card.getPicUrl().split("-");
        picUrl.setCount(url.length);
        picUrl.setUrls(url);
        return picUrl;
    }

    //根据cardId可以提取相应的视频url，通过json发给前端
    @ResponseBody
    @RequestMapping(value = "/videourl/{id}",method = RequestMethod.GET)
    public Object getvideourl(@PathVariable String id){

        PicUrl picUrl = new PicUrl();

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Card card = (Card)redisTemplate.opsForValue().get("Card_cardId:"+id);
        if(card==null){
            card =cardDao.selectById(id);
            redisTemplate.opsForValue().set("Card_cardId:"+id,card);
        }
        String url[] = card.getVideoUrl().split("-");
        picUrl.setCount(url.length);
        picUrl.setUrls(url);
        return picUrl;
    }





}





/*
*
* package com.taotie.wechatpro.controller;


import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.taotie.wechatpro.service.impl.CardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Controller
@RequestMapping("/v1")
public class CardController {
    @Autowired
    CardServiceImpl cardService;

    @ResponseBody
    @RequestMapping(value = "/upCard",method = RequestMethod.POST)
    public Boolean upDisLike(@RequestParam(required = false,value = "files")MultipartFile[] multipartFiles ,@RequestParam@RequestBody String str) throws IOException {
        //完成正常json数据上传
        System.out.println(str);
        Boolean bool = cardService.upCard(str,multipartFiles);
        //如果遇到了不支持类型则返回false
        return bool;
    }


    @ResponseBody
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public Boolean test(@RequestParam@RequestBody String str) throws IOException {
        //完成正常json数据上传
        System.out.println(str);
        //Integer lableId = Integer.parseInt(JSON.parseObject(str).get("lableId").toString());
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(str);
        System.out.println(json.has("lableId"));
        //如果遇到了不支持类型则返回false
        return true;
    }


}

*
*
* */
