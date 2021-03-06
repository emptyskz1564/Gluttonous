package com.taotie.wechatpro.dao.associateTable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.association.DiscussUserLike;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 创建时间: 2020/4/8 16:33
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface DiscussUserLikeDao extends BaseMapper<DiscussUserLike> {

    @Select("select * from discuss_user_like where user_id=#{userId}")
    List<DiscussUserLike> selectByuserId(@Param("userId") Integer userId);

    @Select("select * from discuss_user_like where discuss_id=#{disId}")
    List<DiscussUserLike> selectBydisId(@Param("disId") Integer disId);

    //可以先查看数据库有没有再加入
    @Insert("insert ignore into discuss_user_like (user_id,discuss_id) values (#{userId},#{disId})")
    void insertDisUserLike(@Param("userId") Integer userId, @Param("disId") Integer disId);
}
