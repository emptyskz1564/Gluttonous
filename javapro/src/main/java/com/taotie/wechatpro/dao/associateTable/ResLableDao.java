package com.taotie.wechatpro.dao.associateTable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.association.CardUserLike;
import com.taotie.wechatpro.pojo.association.ResLable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 创建时间: 2020/4/8 16:34
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface ResLableDao extends BaseMapper<ResLable> {
    @Select("select * from res_lable where res_id=#{resId}")
    List<ResLable> selectByresId(@Param("resId") Integer resId);
}
