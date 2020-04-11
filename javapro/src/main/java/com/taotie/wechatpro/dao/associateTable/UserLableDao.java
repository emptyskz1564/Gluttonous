package com.taotie.wechatpro.dao.associateTable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.association.UserLable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 创建时间: 2020/4/8 16:34
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface UserLableDao extends BaseMapper<UserLable> {
    @Insert("insert ignore into user_lable (lable_id,user_id) values (#{lableId},#{userId})")
    void insertUserLable(@Param("lableId") Integer lableId, @Param("userId") Integer userId);
}
