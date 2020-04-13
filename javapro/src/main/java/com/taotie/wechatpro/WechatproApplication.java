package com.taotie.wechatpro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//把jar包改成war包，所以换成了下面的，上面的就是正常jar包
//@SpringBootApplication
//@MapperScan("com.taotie.wechatpro.dao")
//public class WechatproApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(WechatproApplication.class, args);
//    }
//
//}

@MapperScan("com.taotie.wechatpro.dao")
@SpringBootApplication
public class WechatproApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WechatproApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(WechatproApplication.class, args);
    }
}

