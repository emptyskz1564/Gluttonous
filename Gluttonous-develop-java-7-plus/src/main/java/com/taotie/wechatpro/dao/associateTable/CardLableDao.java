package com.taotie.wechatpro.dao.associateTable;

import com.taotie.wechatpro.pojo.association.CardLable;
import org.apache.ibatis.annotations.Insert;
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
public interface CardLableDao {
    @Select("select * from card_lable where card_id=#{cardId}")
    CardLable selectByCardId(@Param("cardId") String cardId);

    @Select("select * from card_lable where lable_id=#{lableId}")
    CardLable selectByLableId(@Param("lableId") String lableId);

    @Select("select * from card_lable where card_id=#{cardId}")
    List<CardLable> selectBycardId(@Param("cardId") Integer cardId);

    @Select("select * from card_lable where lable_id=#{lableId}")
    List<CardLable> selectBylableId(@Param("lableId") Integer lableId);

    @Insert("insert ignore into card_lable (card_id,lable_id) values (#{cardId},#{lableId})")
    void insertCardLable(@Param("cardId") Integer cardId, @Param("lableId") Integer lableId);
}
