package com.taotie.wechatpro.pojo;

import lombok.*;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Configuration
public class CardUser implements Serializable {
    private Integer cardId;
    private Integer resId;
    private String cardContent;
    private String cardTitle;
    private String selfLable1;
    private String selfLable2;
    private String selfLable3;
    private String picUrl;
    private String videoUrl;
    private String wxId;
    private String bestFood;

    private Integer userId;
    private String userName;
    private String userUrl;

    private Integer cardLike;
}
