package com.taotie.wechatpro.controller;

import com.alibaba.fastjson.JSONObject;
import com.taotie.wechatpro.dao.*;
import com.taotie.wechatpro.dao.view.*;
import com.taotie.wechatpro.pojo.*;
import com.taotie.wechatpro.pojo.view.VDiscussUserLike;
import com.taotie.wechatpro.pojo.view.VUserLable;
import com.taotie.wechatpro.pojo.view.VUserRestaurant;
import com.taotie.wechatpro.service.RedisService;
import com.taotie.wechatpro.service.UserService;
import com.taotie.wechatpro.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 创建时间: 2020/4/5 11:09
 * 文件备注:测试
 * 编写人员:杨伯益
 */


//GET、POST、PUT、DELETE。它们分别对应四种基本操作：GET 用来获取资源，POST 用来新建资源，PUT 用来更新资源，DELETE 用来删除资源

@Controller
@ResponseBody//@ResponseBody的作用其实是将java对象转为json格式的数据
@RequestMapping("/v1")
public class HelloController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    VUserRestaurantDao vUserRestaurantDao;

    @Autowired
    VUserLableDao vUserLableDao;

    @Autowired
    VResLableDao vResLableDao;

    @Autowired
    VDiscussUserLikeDao vDiscussUserLikeDao;

    @Autowired
    VCardUserLikeDao vCardUserLikeDao;

    @Autowired
    VCardLableDao vCardLableDao;

    @Autowired
    UserDao userDao;

    @Autowired
    CardDao cardDao;

    @Autowired
    DiscussDao discussDao;

    @Autowired
    RedisService redisServiceImpl;

    @Autowired
    LableDao lableDao;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @RequestMapping(value = "/hello/{id}",method = RequestMethod.GET)
    public String hello(@PathVariable String id) {//@PathVariable可以用来映射URL中的占位符到目标方法的参数中
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("test","测试");
            //String text = redisServiceImpl.get("test",String.class);
            //System.out.println(text);

//            List<User> users = redisServiceImpl.get("test",List.class);
//            for (User user : users) {
//                System.out.println(user);
//            }
            //throw new MyException(200,"123s含");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return jsonObject.toJSONString();
        }

    }

    //根据id返回user表信息
    @ResponseBody
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public Object getUser(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        //首先尝试从redis取出
        User user = (User) redisTemplate.opsForValue().get("userId:"+id);
        if(user==null){
            //没有就从数据库取，然后存入redis
            //key为id，value为user对象
            user = userDao.selectById(id);
            redisTemplate.opsForValue().set("userId:"+id,user);
        }
        return user;
    }

    //根据card_id返回card表信息
    @ResponseBody
    @RequestMapping(value = "/card/{id}",method = RequestMethod.GET)
    public Object getCard(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Card card = (Card) redisTemplate.opsForValue().get("cardId:"+id);
        if(card==null){
            card = cardDao.selectById(id);
            redisTemplate.opsForValue().set("cardId:"+id,card);
        }
        return card;
    }

    //根据id返回discuss表信息
    @ResponseBody
    @RequestMapping(value = "/discuss/{id}",method = RequestMethod.GET)
    public Object getDiscuss(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Discuss discuss = (Discuss) redisTemplate.opsForValue().get("discussId:"+id);
        if(discuss==null){
            discuss = discussDao.selectById(id);
            redisTemplate.opsForValue().set("discussId:"+id,discuss);
        }
        return discuss;
    }

    //根据lable_id返回lable表信息
    @ResponseBody
    @RequestMapping(value = "/lable/{id}",method = RequestMethod.GET)
    public Object getLable(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Lable lable = (Lable) redisTemplate.opsForValue().get("lableId:"+id);
        if(lable==null){
            lable = lableDao.selectById(id);
            redisTemplate.opsForValue().set("lableId:"+id,lable);
        }
        return lable;
    }

    //根据res_id返回restaurant表信息
    @ResponseBody
    @RequestMapping(value = "/restaurant/{id}",method = RequestMethod.GET)
    public Object getRestaurant(@PathVariable String id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Restaurant restaurant = (Restaurant) redisTemplate.opsForValue().get("restaurantId:"+id);
        if(restaurant==null){
            restaurant = restaurantDao.selectById(id);
            redisTemplate.opsForValue().set("restaurantId:"+id,restaurant);
        }
        return restaurant;
    }




    //根据card_id返回v_card_lable表信息
    @ResponseBody
    @RequestMapping(value = "/vcardlable/{id}",method = RequestMethod.GET)
    public Object getvcardlable(@PathVariable String id){
        return vCardLableDao.selectByCardId(Integer.valueOf(id));
    }

    //根据card_id返回v_card_user_like表信息
    @ResponseBody
    @RequestMapping(value = "/vcarduserlike/{id}",method = RequestMethod.GET)
    public Object getvcarduserlike(@PathVariable String id){
        return vCardUserLikeDao.selectByCardId(Integer.valueOf(id));
    }

    //根据card_id返回v_discuss_user_like表信息
    @ResponseBody
    @RequestMapping(value = "/vdiscussuserlike/{id}",method = RequestMethod.GET)
    public Object getvdiscussuserlike(@PathVariable String id){
        return vDiscussUserLikeDao.selectByUserId(Integer.valueOf(id));
    }

    //根据card_id返回v_res_lable表信息
    @ResponseBody
    @RequestMapping(value = "/vreslable/{id}",method = RequestMethod.GET)
    public Object getvreslable(@PathVariable String id){
        return vResLableDao.selectByResId(Integer.valueOf(id));
    }

    //根据card_id返回v_user_lable表信息
    @ResponseBody
    @RequestMapping(value = "/vuserlable/{id}",method = RequestMethod.GET)
    public Object getvuserlable(@PathVariable String id){
        return vUserLableDao.selectByUserId(Integer.valueOf(id));
    }

    //根据card_id返回v_user_restaurant表信息
    @ResponseBody
    @RequestMapping(value = "/vuserrestaurant/{id}",method = RequestMethod.GET)
    public Object getvuserrestaurant(@PathVariable String id){
        return vUserRestaurantDao.selectByUserId(Integer.valueOf(id));
    }






    //获取所有用户
    @ResponseBody
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public Object getUsers(){
        return userService.getUserList();
    }
}
