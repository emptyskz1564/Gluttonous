package com.taotie.wechatpro.dao.associateTable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.association.UserRestaurant;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    List<UserRestaurant> selectByresId(@Param("resId")Integer resId);

    //许祁加的多对多
    @Delete("delete from user_restaurant where user_id=#{userId} and res_id=#{resId}")
    void deletByuserIdresId(@Param("userId") Integer userId, @Param("resId") Integer resId);
}
