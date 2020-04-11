package com.taotie.wechatpro.pojo.association;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/8 16:29
 * 文件备注:
 * 编写人员:
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("user_lable")
public class UserLable implements Serializable {

    private Integer lableId;
    private Integer userId;
}
