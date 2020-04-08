package com.taotie.wechatpro.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * 创建时间: 2020/4/5 11:52
 * 文件备注:
 * 编写人员:
 */


@Mapper
public interface UserDao extends BaseMapper<User> {

}
