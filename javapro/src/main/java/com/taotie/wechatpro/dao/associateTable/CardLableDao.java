package com.taotie.wechatpro.dao.associateTable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.association.CardLable;
import com.taotie.wechatpro.pojo.view.VCardUserDiscuss;
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
    CardLable selectByCardId(@Param("cardId")String cardId);

    @Select("select * from card_lable where lable_id=#{lableId}")
    CardLable  selectByLableId(@Param("lableId")String lableId);

    @Insert("insert into card_lable values(#{cardId},#{lableId})")
    void insert(@Param("cardId") Integer cardId, @Param("lableId")Integer lableId);
}
