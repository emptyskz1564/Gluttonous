package com.taotie.wechatpro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.Card;
import com.taotie.wechatpro.pojo.CardUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 创建时间: 2020/4/5 21:43
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface CardDao extends BaseMapper<Card> {
    //追加picUrl
    @Update("update card set pic_url=CONCAT(pic_url,#{picUrl}) where card_id=#{cardId}")
    void concatPicUrl(@Param("picUrl") String picUrl, @Param("cardId") Integer cardId);

    //追加videoUrl
    @Update("update card set video_url=CONCAT(video_url,#{videoUrl}) where card_id=#{cardId}")
    void concatVideoUrl(@Param("videoUrl") String videoUrl, @Param("cardId") Integer cardId);

    @Select("select x.card_like,y.* from (select a.*,`user`.user_id,`user`.user_name,`user`.user_url from (select * from card) a left join `user` on a.wx_id=`user`.wx_id) y left join (select count(*) as card_like,card_id from card_user_like group by card_id) x on y.card_id=x.card_id")
    List<CardUser> selectAllCardUser();

}
