package com.taotie.wechatpro.pojo.view;

import com.baomidou.mybatisplus.annotation.TableName;
import com.taotie.wechatpro.pojo.User;
import lombok.*;

import java.io.Serializable;

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
public class VUserRestaurant  implements Serializable {

    private Integer userId;
    private String userName;
    private String userUrl;
    private Integer vip;
    private String wxId;
    private Integer resId;
    private String resTitle;
    private String resAdress;
}
