package com.taotie.wechatpro.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * 创建时间: 2020/4/7 18:14
 * 文件备注:
 * 编写人员:
 */

public interface ReceivCard {

    public void receiv(String jsondata) throws FileNotFoundException;
}
