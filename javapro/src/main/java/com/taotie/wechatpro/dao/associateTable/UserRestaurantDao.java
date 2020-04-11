package com.taotie.wechatpro.dao.associateTable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.association.UserRestaurant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 创建时间: 2020/4/8 16:35
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface UserRestaurantDao extends BaseMapper<UserRestaurant> {

    @Select("select * from user_restaurant where user_id=#{userId}")
    UserRestaurant selectByUserId(@Param("userId")Integer userId);
}
