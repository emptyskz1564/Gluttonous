package com.taotie.wechatpro.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2020/4/5 15:47
 * 文件备注: json转化为pojo
 * 编写人员:
 */

public class ConvertUtil {

    public static <T> T json2pojo(String json,Class<T> className){
        if(className==Integer.class){
            return (T) Integer.valueOf(json);
        }else if(className==Long.class){
            return (T) Long.valueOf(json);
        }else if(className==String.class){
            return (T) String.valueOf(json);
        }else{
            return JSON.toJavaObject(JSON.parseObject(json),className);
        }
    }

    public static <T> List<T> json2List(String json,Class<T> className){
        List<T> list = JSONArray.parseArray(json,className);





        return list;
    }
}
