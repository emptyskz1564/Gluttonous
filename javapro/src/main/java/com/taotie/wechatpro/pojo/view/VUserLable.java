package com.taotie.wechatpro.pojo.view;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

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
public class VUserLable implements Serializable {

    private Integer userId;
    private String userName;
    private Integer vip;
    private String wxId;
    private Integer lableId;
    private String lableContent;
    private Integer lableType;
}
