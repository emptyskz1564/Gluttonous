package com.taotie.wechatpro.dao.view;

import com.taotie.wechatpro.pojo.view.VCardLable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 创建时间: 2020/4/6 9:56
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface VCardLableDao {

    @Select("select * from v_card_lable where card_id=#{cardId}")
    VCardLable selectByCardId(@Param("cardId") Integer id);
}
