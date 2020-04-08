package com.taotie.wechatpro.exceptions;

import lombok.*;

/**
 * 创建时间: 2020/4/5 11:03
 * 文件备注:
 * 编写人员:杨伯益
 */
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MyException extends Exception{

    private int code;
    private String message;

}
