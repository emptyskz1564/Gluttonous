package com.taotie.wechatpro;

import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * 创建时间: 2020/4/10 16:27
 * 文件备注:
 * 编写人员:
 */


public class test {

    @Test
    public void test1(){
        String url = "D:\\apps\\git\\coding\\Gluttonous\\javapro\\rocket.png";
        File file = new File(url);
        System.out.println(file.delete());
    }
}
