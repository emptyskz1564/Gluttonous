package com.taotie.wechatpro.pojo.association;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/8 16:25
 * 文件备注:
 * 编写人员:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Configuration
@TableName("card_user_like")
public class CardUserLike implements Serializable {

    private Integer cardId;
    private Integer userId;
}
