package com.taotie.wechatpro.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.User;
import com.taotie.wechatpro.pojo.view.VCardUserDiscuss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * 创建时间: 2020/4/5 11:52
 * 文件备注:
 * 编写人员:
 */


@Mapper
public interface UserDao extends BaseMapper<User> {
    @Select("select wx_id from user where user_id=#{userId}")
    String selectWx_idByUserId(@Param("userId") Integer userId);

    @Update("update user set user_name=#{userName} where user_id=#{userId}")
    void updateUserNameByUserId(@Param("userName") String userName, @Param("userId") Integer userId);

}
