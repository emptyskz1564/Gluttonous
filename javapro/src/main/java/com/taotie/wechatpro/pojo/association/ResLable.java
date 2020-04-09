package com.taotie.wechatpro.pojo.association;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 创建时间: 2020/4/8 16:28
 * 文件备注:
 * 编写人员:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("res_lable")
public class ResLable {

    private Integer lableId;
    private Integer resId;
}
