package com.taotie.wechatpro.pojo.association;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 创建时间: 2020/4/8 16:26
 * 文件备注:
 * 编写人员:
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("discuss_user_like")
public class DiscussUserLike {

    private Integer discussId;
    private Integer userId;

}
