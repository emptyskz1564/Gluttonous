package com.taotie.wechatpro.pojo.association;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 创建时间: 2020/4/8 16:26
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
@TableName("discuss_user_like")
public class DiscussUserLike {

    private Integer discussId;
    private Integer userId;

}
