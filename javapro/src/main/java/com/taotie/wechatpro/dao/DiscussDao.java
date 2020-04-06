package com.taotie.wechatpro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taotie.wechatpro.pojo.Discuss;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 创建时间: 2020/4/5 21:52
 * 文件备注:
 * 编写人员:
 */

@Mapper
public interface DiscussDao extends BaseMapper<Discuss> {
}
