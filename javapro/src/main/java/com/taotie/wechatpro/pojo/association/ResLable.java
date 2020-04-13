package com.taotie.wechatpro.pojo.association;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * 创建时间: 2020/4/8 16:28
 * 文件备注:
 * 编写人员:
 */

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@TableName("res_lable")
public class ResLable implements Serializable {

    private Integer lableId;
    private Integer resId;
}
