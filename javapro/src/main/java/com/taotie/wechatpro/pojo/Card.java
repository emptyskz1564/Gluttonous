package com.taotie.wechatpro.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/5 21:40
 * 文件备注:
 * 编写人员:杨伯益
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Card implements Serializable {

    @TableId("card_id")
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



}
