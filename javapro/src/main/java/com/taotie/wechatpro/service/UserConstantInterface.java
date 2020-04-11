package com.taotie.wechatpro.service;

/**
 * 创建时间: 2020/4/11 14:35
 * 文件备注:
 * 编写人员:
 */


public interface UserConstantInterface {
    // 请求的网址
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    // 你的appid
    public static final String WX_LOGIN_APPID = "xxxxxxxxxxxxxxxxxx";
    // 你的密匙
    public static final String WX_LOGIN_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    // 固定参数
    public static final String WX_LOGIN_GRANT_TYPE = "authorization_code";

}
