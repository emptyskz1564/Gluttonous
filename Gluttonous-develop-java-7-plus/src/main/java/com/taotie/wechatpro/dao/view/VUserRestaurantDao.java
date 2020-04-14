package com.taotie.wechatpro.dao.view;

import com.taotie.wechatpro.pojo.view.VUserRestaurant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 创建时间: 2020/4/6 14:54
 * 文件备注:
 * 编写人员:
 */

public interface VUserRestaurantDao {

    @Select("select * from v_user_restaurant where user_id=#{userId}")
    VUserRestaurant selectByUserId(@Param("userId") Integer userId);
}
