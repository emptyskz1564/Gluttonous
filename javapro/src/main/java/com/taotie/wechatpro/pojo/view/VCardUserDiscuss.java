package com.taotie.wechatpro.pojo.view;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/8 19:33
 * 文件备注:
 * 编写人员: 杨伯益
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("v_card_user_discuss")
public class VCardUserDiscuss implements Serializable {

    private Integer userId;
    private String userName;
    private Integer vip;
    private String userUrl;
    private Integer cardId;
    private Integer carsLike;
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
    private Integer disId;
    private String disComment;
    private Integer parentId;
    private String disThread;
    private Integer disUserId;
    private Integer disLike;
}
