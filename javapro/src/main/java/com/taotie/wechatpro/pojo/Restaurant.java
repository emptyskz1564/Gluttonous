package com.taotie.wechatpro.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/6 14:57
 * 文件备注:
 * 编写人员:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Restaurant implements Serializable {
    @TableId("res_id")
    private Integer resId;
    private String resTitle;
    private String resAdress;
}
