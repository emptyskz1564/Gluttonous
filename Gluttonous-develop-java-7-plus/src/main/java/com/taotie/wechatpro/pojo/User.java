package com.taotie.wechatpro.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/5 11:29
 * 文件备注:
 * 编写人员:杨伯益
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Configuration
public class User implements Serializable {

    @TableId("user_id")
    private Integer userId;
    private String userName;
    private Integer vip;//0假1真
    private String wxId;
    private String userUrl;
    private String userOpenid;


}
