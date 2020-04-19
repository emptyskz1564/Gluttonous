package com.taotie.wechatpro.controller.set;


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
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public Integer upchangeUser(@RequestParam @RequestBody String str){
        userService.upchangeUser(str);
        return 1;
    }

    //用户收藏餐馆
    @ResponseBody
    @RequestMapping(value = "/userrestaurant/like/{user_id}/{restaurant_id}",method = RequestMethod.POST)
    public Integer upUserLikeRes(@PathVariable String user_id,@PathVariable String restaurant_id){
        Integer userId = Integer.valueOf(user_id);
        Integer resId = Integer.valueOf(restaurant_id);
        userService.upUserLikeRes(userId,resId);
        return 1;
    }


    //用户取消收藏
    @ResponseBody
    @RequestMapping(value = "/userrestaurant/hate/{user_id}/{restaurant_id}",method = RequestMethod.POST)
    public Integer upUserHateRes(@PathVariable String user_id,@PathVariable String restaurant_id){
        Integer userId = Integer.valueOf(user_id);
        Integer resId = Integer.valueOf(restaurant_id);
        userService.upUserHateRes(userId,resId);
        return 1;
    }



    /****************************************************************************/
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
