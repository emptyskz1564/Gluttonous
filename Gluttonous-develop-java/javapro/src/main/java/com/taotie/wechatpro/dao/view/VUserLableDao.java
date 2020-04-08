package com.taotie.wechatpro.dao.view;

import com.taotie.wechatpro.pojo.view.VUserLable;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 创建时间: 2020/4/6 14:48
 * 文件备注:
 * 编写人员:
 */

public interface VUserLableDao {

    @Select("select * from v_user_lable where user_id=#{userId}")
    VUserLable selectByUserId(@Param("userId")Integer userId);
}
