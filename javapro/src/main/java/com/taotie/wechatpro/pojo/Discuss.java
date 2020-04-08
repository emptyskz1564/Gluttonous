package com.taotie.wechatpro.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/5 21:51
 * 文件备注:
 * 编写人员: 杨伯益
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Discuss implements Serializable {

    @TableId("dis_id")
    private Integer disId;
    private Integer cardId;
    private String disComment;
    private Integer parentId;
    private String disThread;
    private Integer disUserId;
}
