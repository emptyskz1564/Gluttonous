package com.taotie.wechatpro.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.User;
import org.apache.ibatis.annotations.*;

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

    @Select("select user_id from user where user_openid=#{userOpenid}")
    Integer selectUserIdByUserOpenid(@Param("userOpenid") String userOpenid);

    @Insert("insert ignore into user(user_openid,vip) values (#{userOpenid},#{vip})")
    void insertemptyUseropenId(@Param("userOpenid") String userOpenid,@Param("vip") Integer vip);

    @Update("update user set user_name=#{userName} where user_id=#{userId}")
    void updateUserNameByUserId(@Param("userName") String userName, @Param("userId") Integer userId);

    //许祁说加的一个修改让wxId=userId
    @Update("update user set wx_id=#{userId} where user_id=#{userId}")
    void updateWxIdByUserId(@Param("userId") Integer userId);

    @Select("select * from user where user_id=#{userId}")
    List<User> selectByuserId(@Param("userId") Integer userId);

}
