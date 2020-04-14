package com.taotie.wechatpro.dao.associateTable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.association.UserRestaurant;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 创建时间: 2020/4/8 16:35
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface UserRestaurantDao extends BaseMapper<UserRestaurant> {

    @Select("select * from user_restaurant where user_id=#{userId}")
    List<UserRestaurant> selectByuserId(@Param("userId") Integer userId);

    @Select("select * from user_restaurant where res_id=#{resId}")
    List<UserRestaurant> selectByresId(@Param("resId") Integer resId);

    //许祁加的多对多
    @Delete("delete from user_restaurant where user_id=#{userId} and res_id=#{resId}")
    void deletByuserIdresId(@Param("userId") Integer userId, @Param("resId") Integer resId);

    //可以先查看数据库有没有再加入
    @Insert("insert ignore into user_restaurant (user_id,res_id) values (#{userId},#{resId})")
    void insertUserRes(@Param("userId") Integer userId, @Param("resId") Integer resId);
}
