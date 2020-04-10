package com.taotie.wechatpro.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.taotie.wechatpro.dao.CardDao;
import com.taotie.wechatpro.dao.UserDao;
import com.taotie.wechatpro.dao.associateTable.CardLableDao;
import com.taotie.wechatpro.dao.associateTable.CardUserLikeDao;
import com.taotie.wechatpro.pojo.Card;
import com.taotie.wechatpro.pojo.Discuss;
import com.taotie.wechatpro.pojo.association.CardLable;
import com.taotie.wechatpro.pojo.association.CardUserLike;
import com.taotie.wechatpro.service.CardService;
import com.taotie.wechatpro.utils.FileUpDownUtil;
import com.taotie.wechatpro.utils.FileUtil;
import com.taotie.wechatpro.utils.UUIDUtil;
import com.taotie.wechatpro.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

@Service("CardServiceImpl")
public class CardServiceImpl implements CardService {

    @Autowired
    CardUserLike carduserlike;

    @Autowired
    CardUserLikeDao carduserlikeDao;

    @Autowired
    UserDao userDao;

    @Autowired
    CardDao cardDao;

    @Autowired
    CardLableDao cardLableDao;

    @Override
    public void upCardLike(String str) {
        Integer cardId = Integer.parseInt(JSON.parseObject(str).get("cardId").toString());
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        carduserlike.setCardId(cardId);
        carduserlike.setUserId(userId);
        carduserlikeDao.insert(carduserlike);
    }

    public Boolean upCard(String str,MultipartFile[] multipartFiles) throws IOException {
        Boolean bool = new Boolean(true);

        //google的用来判断json串中某key是否存在（避免造成空指针）(lableId可能不存在)
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(str);

        Card card = JSON.parseObject(str, (Type) Card.class);
        //因为刘sh不好拿wxId只能给userId，这个地方就去user表查找到wxId再放进实体pojo
        String wx_id = userDao.selectWx_idByUserId(Integer.parseInt(JSON.parseObject(str).get("userId").toString()));
        Integer cardId = UUIDUtil.getUUIDInOrderId();
        //这个地方需要插入关联表card_lable于是需要单独拿出来
        //需要对lableId1,lableId2,lableId3进行存在和值存在的判断，规定值为-1则客户没选次lable

        Integer[] lableIds = new Integer[3];
        int k = 0;
        for(int i = 1; i <= 3; i++) {
            if(json.has("lableId"+i)){
                Integer m = Integer.parseInt(JSON.parseObject(str).get("lableId"+i).toString());
                if(m != -1){
                    lableIds[k] = m;
                    k++;
                }
            }
        }
        //此时的k会比预期的大1
        k--;

        //Integer lableId = Integer.parseInt(JSON.parseObject(str).get("lableId").toString());

        card.setCardId(cardId);
        card.setWxId(wx_id);

        //完成图片与视频的上传与URL返回加入pojo实体
        if(multipartFiles != null){
            //图片与视频的多个URL的合并String
            StringBuilder pictureUrl = new StringBuilder();
            StringBuilder videoUrl = new StringBuilder();


            //修改的地方
            File f = null;
            StringBuffer path = new StringBuffer("");
            String temp = null;


            for (MultipartFile multipartFile : multipartFiles){
                String filename = multipartFile.getOriginalFilename();
                System.out.println(filename);
                //判断文件类型（似乎不需要）
                //String contentType = multipartFile.getContentType().substring(multipartFile.getContentType().lastIndexOf("/")+1);
                //获取后缀
                String substring = filename.substring(filename.lastIndexOf("."));
                System.out.println(substring);
                //将multipartFile变为file
                InputStream ins = multipartFile.getInputStream();

                //修改的地方
                //File f = new File(multipartFile.getOriginalFilename());
                f = new File(multipartFile.getOriginalFilename());




                //新建的util类用来进行转换
                FileUtil.inputStreamToFile(ins,f);

                //得到上传文件的fileinputstream
                FileInputStream fileInputStream = new FileInputStream(f);





                //判断后缀，图片,视频else返回上传失败,调用七牛云接口完成上传和URL返回
                if(substring.equals(".jpg") || substring.equals(".png")) {
                    System.out.println("第一层");
                    if(fileInputStream!=null) {
                        System.out.println("第二层");
                        String picUrl = FileUpDownUtil.upload(fileInputStream);
                        pictureUrl.append(picUrl+"-");
                    }else {
                        String picUrl = null;
                    }
                }else if(substring.equals(".mp4")) {
                    if(fileInputStream!=null) {
                        String vidUrl = FileUpDownUtil.upload(fileInputStream);
                        videoUrl.append(vidUrl+"-");
                    }else {
                        String vidUrl = null;
                    }
                }else {
                    //文件类型不符合，response出错
                    bool = false;
                    return bool;
                }



                //修改的地方
                fileInputStream.close();

                //获取图片路径，上传结束后删除掉本地出现的图片文件
                temp = System.getProperty("user.dir");//获取当前工程的目录
                path.delete(0,path.length());//对每一个文件，首先清空路径内容，重新赋值
                path.append(temp);
                path.append("\\"+f.getName());//获取文件名
                System.out.println("是否删除成功？");
                System.out.println(path);
                File file = new File(String.valueOf(path));
                System.out.println(file.delete());




            }

            card.setPicUrl(pictureUrl.toString());
            card.setVideoUrl(videoUrl.toString());
        }

        cardDao.insert(card);
        //向关联表增加
        while (k >= 0) {
            System.out.println(lableIds[k]);
            cardLableDao.insert(cardId, lableIds[k]);
            k--;
        }

        return bool;

    }

    /**public void  upCard2(MultipartFile[] multipartFiles) throws IOException {
        if(multipartFiles.length > 0){
            //图片与视频的多个URL的合并String
            StringBuilder pictureUrl = new StringBuilder();
            StringBuilder videoUrl = new StringBuilder();

            for (MultipartFile multipartFile : multipartFiles){
                String filename = multipartFile.getOriginalFilename();
                //判断文件类型
                String contentType = multipartFile.getContentType().substring(multipartFile.getContentType().lastIndexOf("/")+1);
                //获取后缀
                String substring = filename.substring(filename.lastIndexOf("."));
                //将multipartFile变为file
                InputStream ins = multipartFile.getInputStream();
                File f = new File(multipartFile.getOriginalFilename());
                FileUtil.inputStreamToFile(ins,f);

                //得到上传文件的fileinputstream
                FileInputStream fileInputStream = new FileInputStream(f);

                //判断后缀，图片,视频else返回上传失败,调用七牛云接口完成上传和URL返回
                if(substring == "png" || substring == "jpg") {
                    if(fileInputStream!=null) {
                        String picUrl = FileUpDownUtil.upload(fileInputStream);
                        pictureUrl.append(picUrl+"|");
                    }else {
                        String picUrl = null;
                    }
                }
                if(substring == "mp4") {
                    if(fileInputStream!=null) {
                        String vidUrl = FileUpDownUtil.upload(fileInputStream);
                        videoUrl.append(vidUrl+"|");
                    }else {
                        String vidUrl = null;
                    }
                }else {
                    //文件类型不符合，response出错

                }
            }


        }
    }**/
}
