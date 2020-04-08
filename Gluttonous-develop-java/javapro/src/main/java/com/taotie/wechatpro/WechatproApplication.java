package com.taotie.wechatpro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.taotie.wechatpro.dao")
public class WechatproApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatproApplication.class, args);
    }

}
