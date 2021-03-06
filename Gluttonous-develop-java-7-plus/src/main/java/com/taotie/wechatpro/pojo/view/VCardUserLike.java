package com.taotie.wechatpro.pojo.view;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/6 14:20
 * 文件备注:
 * 编写人员:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("v_card_user_like")
public class VCardUserLike implements Serializable {

    private Integer cardId;
    private Integer resId;
    private String cardContent;
    private String selfLable1;
    private String selfLable2;
    private String selfLable3;
    private String picUrl;
    private String videoUrl;
    private String wxId;
    private String bestFood;
    private String userId;
    private String userName;
    private String userUrl;
    private Integer vip;




}
