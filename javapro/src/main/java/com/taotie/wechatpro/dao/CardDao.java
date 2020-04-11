package com.taotie.wechatpro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.Card;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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
}
