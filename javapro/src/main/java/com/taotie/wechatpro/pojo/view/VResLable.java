package com.taotie.wechatpro.pojo.view;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/6 14:42
 * 文件备注:
 * 编写人员:
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("v_res_lable")
public class VResLable implements Serializable {

    private Integer resId;
    private String resTitle;
    private String resAdress;
    private Integer lableId;
    private String lableContent;
    private Integer lableType;
}
