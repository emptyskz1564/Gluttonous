package com.taotie.wechatpro.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import org.springframework.context.annotation.Configuration;

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
@Configuration
public class Discuss implements Serializable {

    @TableId(value = "dis_id", type = IdType.AUTO)
    private Integer disId;
    private Integer cardId;
    private String disComment;
    private Integer parentId;
    private String disThread;
    private Integer disUserId;
    private String userName;
    private Integer discussLike;
}
