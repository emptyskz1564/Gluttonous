package com.taotie.wechatpro.dao.view;

import com.taotie.wechatpro.pojo.view.VDiscussUserLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 创建时间: 2020/4/6 14:38
 * 文件备注:
 * 编写人员:
 */
@Mapper
public interface VDiscussUserLikeDao {

    @Select("select * from v_discuss_user_like where user_id=#{userId}")
    VDiscussUserLike selectByUserId(@Param("userId")Integer userId);

}
