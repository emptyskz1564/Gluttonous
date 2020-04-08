package com.taotie.wechatpro.pojo.view;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/6 9:53
 * 文件备注:
 * 编写人员:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("v_card_lable")
public class VCardLable implements Serializable {

    @TableField("card_id")
    private Integer cardId;
    private Integer resId;
    private String cardContent;
    private String cardTitle;
    private String selfLable1;
    private String selfLable2;
    private String selfLable3;
    private String picUrl;
    private String videoUrl;
    private String wxId;
    private String bestFood;
    private Integer lableId;
    private String lableContent;
    private Integer lableType;
}
