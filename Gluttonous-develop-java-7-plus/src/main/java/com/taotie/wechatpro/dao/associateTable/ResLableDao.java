package com.taotie.wechatpro.dao.associateTable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.association.ResLable;
import org.apache.ibatis.annotations.Insert;
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

    //可以先查看数据库有没有再加入
    @Insert("insert ignore into res_lable (lable_id,res_id) values (#{lableId},#{resId})")
    void insertResLable(@Param("resId") Integer resId, @Param("lableId") Integer lableId);
}
