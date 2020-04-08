package com.taotie.wechatpro.pojo.view;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 创建时间: 2020/4/8 19:18
 * 文件备注: 许祁实验用
 * 编写人员:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("v_card_detail")
public class VCardDetail {

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
    private String resTitle;
    private String resAdress;
    private String userName;
    private Integer disId;
    private String disComment;
    private Integer parentId;
    private Integer disThread;
}
