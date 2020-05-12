package com.taotie.wechatpro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.Discuss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 创建时间: 2020/4/5 21:52
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface DiscussDao extends BaseMapper<Discuss> {

    //许祁写的
    @Select("select x.discusslike,y.* from (select a.*,`user`.user_name from (select * from discuss as c where card_id=#{cardId} order by substring(c.dis_thread,1,(length(c.dis_thread)-1)) asc) a left join `user` on a.dis_user_id=`user`.user_id order by substring(a.dis_thread,1,(length(a.dis_thread)-1)) asc) y left join (select count(*) as discusslike,discuss_id from discuss_user_like group by discuss_id) x on y.dis_id=x.discuss_id order by substring(y.dis_thread,1,(length(y.dis_thread)-1)) asc")
    List<Discuss> showalldis(@Param("cardId") Integer cardId);

    //"select * from discuss as c where card_id=#{cardId} order by substring(c.dis_thread,1,(length(c.dis_thread)-1)) asc"

    //杨伯益新增部分，应该是降序
    @Select("select * from discuss as c where card_id=#{cardId} order by substring(c.dis_thread,1,(length(c.dis_thread)-1)) desc")
    List<Discuss> showDisDesc(@Param("cardId") Integer cardId);
}
