package com.taotie.wechatpro.dao.view;

import com.taotie.wechatpro.pojo.view.VCardUserDiscuss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 创建时间: 2020/4/8 19:37
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface VCardUserDiscussDao {

    @Select("select * from v_card_user_discuss where user_id=#{userId}")
    List<VCardUserDiscuss> selectByUserId(@Param("userId")String userId);

    @Select("select * from v_card_user_discuss where card_id=#{cardId}")
    List<VCardUserDiscuss> selectByCardId(@Param("cardId")String cardId);
}
