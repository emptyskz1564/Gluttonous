package com.taotie.wechatpro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.Discuss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * 创建时间: 2020/4/5 21:52
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface DiscussDao extends BaseMapper<Discuss> {

    @Select("select * from discuss as c where card_id=#{cardId} order by substring(c.dis_thread,1,(length(c.dis_thread)-1)) asc")
    List<Discuss> showalldis(@Param("cardId") Integer cardId);
}
