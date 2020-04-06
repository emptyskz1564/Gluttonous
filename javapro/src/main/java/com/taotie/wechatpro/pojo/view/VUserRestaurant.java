package com.taotie.wechatpro.pojo.view;

import com.baomidou.mybatisplus.annotation.TableName;
import com.taotie.wechatpro.pojo.User;
import lombok.*;

/**
 * 创建时间: 2020/4/6 14:51
 * 文件备注:
 * 编写人员:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("v_user_restaurant")
public class VUserRestaurant {

    private Integer userId;
    private String userName;
    private Integer vip;
    private String wxId;
    private Integer resId;
    private String resTitle;
    private String resAdress;
}
