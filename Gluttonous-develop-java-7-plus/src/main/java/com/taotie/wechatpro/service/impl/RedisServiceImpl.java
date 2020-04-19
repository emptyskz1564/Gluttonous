package com.taotie.wechatpro.service.impl;

import com.taotie.wechatpro.service.RedisService;
import com.taotie.wechatpro.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2020/4/5 15:40
 * 文件备注:
 * 编写人员:
 */


@Service("redisServiceImpl")
public class RedisServiceImpl implements RedisService {
    @Autowired
    JedisPool jedisPool;

    @Override
    public <T> T get(String key, Class<T> className) {
        Jedis jedis = null;
        T t = null;
        try {
            jedis = jedisPool.getResource();
            String result = jedis.get(key);
            t = ConvertUtil.json2pojo(result,className);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(jedis);
            return t;
        }
    }


    @Override
    public <T>  List<T> getArray(String key, Class<T> className) {
        Jedis jedis = null;
        List<T> items = new ArrayList<>();
        try {
            //jedis = jedisPool.getResource();
            String result = jedis.get(key);
            //t = ConvertUtil.json2pojo(result,className);
            items = ConvertUtil.json2List(result,className);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //close(jedis);
            return items;
        }
    }

    @Override
    public <T> void set(String key, T item) {

    }

    @Override
    public void close(Jedis jedis) {
        if(jedis!=null){
            jedis.close();
        }
    }
}
