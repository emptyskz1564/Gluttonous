package com.taotie.wechatpro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.Restaurant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 创建时间: 2020/4/6 14:59
 * 文件备注:
 * 编写人员:
 */


@Mapper
public interface RestaurantDao extends BaseMapper<Restaurant> {
    //追加picUrl
    @Update("update restaurant set res_url=CONCAT(res_url,#{resUrl}) where res_id=#{resId}")
    void concatResUrl(@Param("resUrl") String resUrl, @Param("resId") Integer resId);

}
