package com.taotie.wechatpro.pojo.someother;

import lombok.*;

/**
 * 创建时间: 2020/4/9 19:34
 * 文件备注:
 * 编写人员:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PicUrl {

    private String urls[];
    private int count;//表示图片url的个数
}
