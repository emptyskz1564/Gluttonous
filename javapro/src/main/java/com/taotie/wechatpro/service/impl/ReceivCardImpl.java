package com.taotie.wechatpro.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.taotie.wechatpro.dao.CardDao;
import com.taotie.wechatpro.pojo.Card;
import com.taotie.wechatpro.service.ReceivCard;
import com.taotie.wechatpro.utils.FileUpDownUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * 创建时间: 2020/4/7 18:15
 * 文件备注:
 * 编写人员:
 */

@Service("ReceivCardImpl")
public class ReceivCardImpl implements ReceivCard {

    @Autowired
    CardDao cardDao;


//    @Override
//    public List<InputStream> pic2stream(String jsondata) throws FileNotFoundException {
//        JSONObject jsonobject = JSONObject.parseObject(jsondata);//获取打卡数据
//        List<String> pic_array = (List<String>) jsonobject.get("cardPic");//把打卡数据中的的图片数据
//        List<InputStream> list = null;
//        for (int i = 0; i < pic_array.size(); i++){
//            InputStream in = new FileInputStream(String.valueOf(i));
//            list.add(in);
//        }
//        FileInputStream in = (FileInputStream)jsonobject.get("cardPic");
//        return list;
//    }

    @Override
    public void receiv(String jsondata) throws FileNotFoundException {
        JSONObject jsonobject = JSONObject.parseObject(jsondata);//获取打卡数据
        //处理图片
        FileInputStream fileInputStream = (FileInputStream)jsonobject.get("cardPic");
        if(fileInputStream!=null) {
            String picUrl = FileUpDownUtil.upload(fileInputStream);
        }else {
            String picUrl = null;
        }
        //处理视频
        fileInputStream = (FileInputStream)jsonobject.get("cardVideo");
        if(fileInputStream!=null) {
            String videoUrl = FileUpDownUtil.upload(fileInputStream);
        }else {
            String videoUrl = null;
        }
        String username = (String)jsonobject.get("userName");
        String cardContent = (String)jsonobject.get("cardContent");
        String selfLable = (String)jsonobject.get("cardLable");
        cardDao.insert(new Card(/**还没写完*/));
    }
}
