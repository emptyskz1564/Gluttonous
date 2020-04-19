package com.taotie.wechatpro.dao.view;

import com.taotie.wechatpro.pojo.view.VCardUserLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 创建时间: 2020/4/6 14:23
 * 文件备注:
 * 编写人员:
 */


@Mapper
public interface VCardUserLikeDao {

    @Select("select * from v_card_user_like where card_id=#{cardId}")
    VCardUserLike selectByCardId(@Param("cardId") Integer cardId);

    @Select("select * from v_card_user_like where user_id=#{userId}")
    List<VCardUserLike> selectByUserId(@Param("userId") Integer userId);

    @Select("select * from v_card_user_like")
    List<VCardUserLike> selectAll();

    @Select("select count(*) from v_card_user_like where card_id=#{cardId}")
    Integer countlike(@Param("cardId") Integer cardId);
}
