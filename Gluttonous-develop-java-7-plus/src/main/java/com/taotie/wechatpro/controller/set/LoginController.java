package com.taotie.wechatpro.controller.set;


import com.alibaba.fastjson.JSONObject;
import com.taotie.wechatpro.dao.UserDao;
import com.taotie.wechatpro.pojo.User;
import com.taotie.wechatpro.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/v1")
public class LoginController {

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    UserDao userDao;

    @ResponseBody
    @RequestMapping("/login/{code}")
    public Integer login(@PathVariable("code") String code) {
        //没写进配置文件，因为这appid和secret基本不会变（用的刘斯昊的）
        String url = "https://api.weixin.qq.com/sns/jscode2session?"+"appid="+"wxbb827deccec135e2"+"&secret="+"7b8183fc998c94f29e7e00d51bc1a077"+"&js_code="+code+"&grant_type=authorization_code";
        JSONObject object = HttpClientUtils.httpGet(url);

        // 成功获取
        String unionid = (String) object.get("unionid");
        String openid = (String) object.get("openid");//用户唯一标识
        // 会话密钥session_key 是对用户数据进行加密签名的密钥。为了应用自身的数据安全，开发者服务器不应该把会话密钥下发到小程序，也不应该对外提供这个密钥。
        //其实这个没啥diao用
        String session_key = (String) object.get("session_key");

        //从redis查看有无，没有就存入数据库（并存入redis）有就直接拿
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Integer userId = (Integer) redisTemplate.opsForValue().get("openid:"+openid);
        if(userId == null){
            userDao.insertemptyUseropenId(openid);
            userId = userDao.selectUserIdByUserOpenid(openid);

            userDao.updateWxIdByUserId(userId);

            //缓存放一天，后面需要再改
            redisTemplate.opsForValue().set("openid:"+openid,userId,1, TimeUnit.DAYS);
        }

        System.out.println("unionid="+unionid);
        System.out.println("openid="+openid);
        System.out.println("session_key="+session_key);

        //用户进入小程序便将信息存入redis，方便后续调用
        User user = userDao.selectById(userId);
        redisTemplate.opsForValue().set("User_userId:"+userId,user);

        return userId;
    }

}
