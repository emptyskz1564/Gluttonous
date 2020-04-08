package com.taotie.wechatpro.pojo.association;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/8 16:22
 * 文件备注:
 * 编写人员:
 */




@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("card_lable")
public class CardLable implements Serializable {


    private Integer cardId;

    private Integer lableId;




}
