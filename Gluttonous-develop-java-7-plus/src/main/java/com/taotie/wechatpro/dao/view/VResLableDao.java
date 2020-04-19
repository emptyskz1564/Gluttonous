package com.taotie.wechatpro.dao.view;

import com.taotie.wechatpro.pojo.view.VResLable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 创建时间: 2020/4/6 14:44
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface VResLableDao {

    @Select("select * from v_res_lable ")
    List<VResLable> selectAll();

    @Select("select * from v_res_lable where res_id=#{resId}")
    List<VResLable> selectByResId(@Param("resId") Integer resId);
}
