package com.taotie.wechatpro.controller;

import com.taotie.wechatpro.service.impl.ReceivCardImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.FileNotFoundException;

/**
 * 创建时间: 2020/4/7 18:16
 * 文件备注:
 * 编写人员:
 */

@Controller
@RequestMapping("/v1")
public class getCardController {

    @Autowired
    ReceivCardImpl receivCard;

    @RequestMapping(value = "/card",method = RequestMethod.POST)
    public void getCard(String jsondata) throws FileNotFoundException {
        receivCard.receiv(jsondata);

    }
}
