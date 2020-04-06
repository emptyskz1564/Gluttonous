package com.taotie.wechatpro.pojo.view;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 创建时间: 2020/4/6 14:47
 * 文件备注:
 * 编写人员:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("v_user_lable")
public class VUserLable {

    private Integer userId;
    private String userName;
    private Integer vip;
    private String wxId;
    private Integer lableId;
    private String lableContent;
    private Integer lableType;
}
