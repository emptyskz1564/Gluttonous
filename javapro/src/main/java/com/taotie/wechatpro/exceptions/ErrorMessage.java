package com.taotie.wechatpro.exceptions;

import lombok.*;

/**
 * 创建时间: 2020/4/5 11:05
 * 文件备注:
 * 编写人员:杨伯益
 */


@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage {

    private int code;
    private String message;



}
