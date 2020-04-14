package com.taotie.wechatpro.controller;


import com.taotie.wechatpro.dao.associateTable.UserRestaurantDao;
import com.taotie.wechatpro.pojo.association.UserRestaurant;
import com.taotie.wechatpro.service.impl.UserServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRestaurantDao userRestaurantDao;

    @Autowired
    RedisTemplate redisTemplate;

    @ResponseBody
    @RequestMapping(value = "/upchangeUser",method = RequestMethod.POST)
    public Integer upchangeUser(@RequestParam @RequestBody String str){
        userService.upchangeUser(str);
        return 1;
    }

    @ResponseBody
    @RequestMapping(value = "/upUserLikeRes",method = RequestMethod.POST)
    public Integer upUserLikeRes(@RequestParam@RequestBody String str){
        userService.upUserLikeRes(str);
        return 1;
    }


    @ResponseBody
    @RequestMapping(value = "/upUserHateRes",method = RequestMethod.POST)
    public Integer upUserHateRes(@RequestParam@RequestBody String str){
        userService.upUserHateRes(str);
        return 1;
    }



    //下面是杨伯益写的测试用的不用管，能用
    @ResponseBody
    @RequestMapping(value = "/upuserres",method = RequestMethod.POST)
    public void upUserRes(@Param("userId")String userId,@Param("resId")String resId){
        //首先存进数据库
        UserRestaurant userRestaurant = new UserRestaurant(Integer.valueOf(userId),Integer.valueOf(resId));
        userRestaurantDao.insert(userRestaurant);

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        //再更新redis
        redisTemplate.opsForValue().set("UserRestaurant_userId:"+userId,userRestaurantDao.selectByuserId(1));
        redisTemplate.opsForValue().set("UserRestaurant_resId:"+resId,userRestaurantDao.selectByresId(1));
    }
}
