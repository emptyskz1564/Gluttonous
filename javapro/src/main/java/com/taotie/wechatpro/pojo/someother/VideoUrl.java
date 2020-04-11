package com.taotie.wechatpro.pojo.someother;

import lombok.*;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/9 19:35
 * 文件备注:
 * 编写人员:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VideoUrl implements Serializable {

    private String urls[];
    private int count;//视频url的个数
}
