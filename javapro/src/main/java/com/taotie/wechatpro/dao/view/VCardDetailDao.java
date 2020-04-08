package com.taotie.wechatpro.dao.view;

import com.taotie.wechatpro.pojo.view.VCardDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 创建时间: 2020/4/8 19:21
 * 文件备注: 许祁test实验用
 * 编写人员:
 */

@Mapper
public interface VCardDetailDao {

    @Select("select * from v_card_detail where card_id=#{cardId}")
    VCardDetail selectByCardId(@Param("cardId")String cardId);
}
