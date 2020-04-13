package com.taotie.wechatpro.dao.associateTable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.association.CardUserLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 创建时间: 2020/4/8 16:32
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface CardUserLikeDao extends BaseMapper<CardUserLike> {


    @Select("select * from card_user_like where user_id=#{userId}")
    List<CardUserLike> selectByuserId(@Param("userId")Integer userId);

    @Select("select * from card_user_like where card_id=#{cardId}")
    List<CardUserLike> selectBycardId(@Param("cardId")Integer cardId);


}
