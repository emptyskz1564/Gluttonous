package com.taotie.wechatpro.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * 创建时间: 2020/4/5 15:39
 * 文件备注:
 * 编写人员:
 */

public interface RedisService {


    public <T> T get(String key,Class<T> className);
    public <T> void set(String key,T item);
    public void close(Jedis jedis);
    public <T> List<T> getArray(String key, Class<T> className);
}
