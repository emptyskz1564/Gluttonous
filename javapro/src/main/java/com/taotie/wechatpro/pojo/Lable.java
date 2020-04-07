package com.taotie.wechatpro.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/5 21:54
 * 文件备注:
 * 编写人员: 杨伯益
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Lable implements Serializable {

    @TableId("lable_id")
    private Integer lableId;
    private String lableContent;
    private Integer lableType;
}
