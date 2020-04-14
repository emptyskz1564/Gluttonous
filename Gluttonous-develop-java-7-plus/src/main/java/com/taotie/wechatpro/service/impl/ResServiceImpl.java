package com.taotie.wechatpro.service.impl;


import com.alibaba.fastjson.JSON;
import com.taotie.wechatpro.dao.RestaurantDao;
import com.taotie.wechatpro.pojo.Restaurant;
import com.taotie.wechatpro.utils.FileUpDownUtil;
import com.taotie.wechatpro.utils.FileUtil;
import com.taotie.wechatpro.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

@Service("ResServiceImpl")
public class ResServiceImpl {

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public Integer upRes1(String str, MultipartFile multipartFile) throws IOException {
        Integer resId = UUIDUtil.getUUIDInOrderId();
        Restaurant restaurant = JSON.parseObject(str, (Type) Restaurant.class);
        restaurant.setResId(resId);
        if(multipartFile != null){
            //图片与视频的多个URL的合并String
            StringBuilder pictureUrl = new StringBuilder();

            //修改的地方
            File f = null;
            StringBuffer path = new StringBuffer("");
            String temp = null;

            String filename = multipartFile.getOriginalFilename();
            System.out.println(filename);
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
            }else {
                //文件类型不符合，response出错
                return -1;
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

            //向pojo实体增加pictureUrl
            restaurant.setResUrl(pictureUrl.toString());
        }
        restaurantDao.insert(restaurant);

        redisTemplate.opsForValue().set("Restaurant_resId:"+resId,restaurant,1, TimeUnit.DAYS);

        return resId;
    }



    public Integer upRes2(String str,MultipartFile multipartFile) throws IOException{
        Integer resId = Integer.parseInt(JSON.parseObject(str).get("resId").toString());
        Restaurant restaurant = (Restaurant) redisTemplate.opsForValue().get("Restaurant_resId:"+resId);
        StringBuilder resurl = new StringBuilder(restaurant.getResUrl());

        if(multipartFile != null){
            //图片与视频的多个URL的合并String
            StringBuilder pictureUrl = new StringBuilder();

            //修改的地方
            File f = null;
            StringBuffer path = new StringBuffer("");
            String temp = null;

            String filename = multipartFile.getOriginalFilename();
            System.out.println(filename);
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
                    resurl.append(picUrl+"-");
                    //直接调用dao向指定cardid中添加URL
                    restaurantDao.concatResUrl(pictureUrl.toString(),resId);
                    restaurant.setResUrl(resurl.toString());
                }else {
                    String picUrl = null;
                }
            }else {
                //文件类型不符合，response出错
                return -1;
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

        redisTemplate.opsForValue().set("Restaurant_resId:"+resId,restaurant,1, TimeUnit.DAYS);
        return resId;
    }
}
