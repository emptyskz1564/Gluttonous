package com.taotie.wechatpro.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * 创建时间: 2020/4/7 11:30
 * 文件备注:
 * 编写人员: 杨伯益
 */


public class filwUpDownUtil {

    //传入一个包含图片或视频的inputStream，返回外链字符串
    public static String upload(FileInputStream fileInputStream) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "BBJfruU970ss5X448tA8Nxk9hu8g49z6_2vjx06F";
        String secretKey = "9WCki9RQwgup0G2cNDKMd73uOfSlmi-2s9XFnQtp";
        String bucket = "xiaochengxuimg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        DefaultPutRet putRet = null;
        try {
            Response response = uploadManager.put(fileInputStream, key, upToken, null, null);
            //解析上传成功的结果
            putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println("外链是：http://q8ee90frk.bkt.clouddn.com/" + putRet.hash);

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                ex2.printStackTrace();
            }
        }finally {
            return "http://q8ee90frk.bkt.clouddn.com/" + putRet.hash;
        }
    }



    //传入文件url，删除成功返回true
    public static boolean delete(String url){
        String key=url.replace("http://q8ee90frk.bkt.clouddn.com/","");//url中去除前面的域名http://q8ee90frk.bkt.clouddn.com/之后就是key值
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        String accessKey = "BBJfruU970ss5X448tA8Nxk9hu8g49z6_2vjx06F";
        String secretKey = "9WCki9RQwgup0G2cNDKMd73uOfSlmi-2s9XFnQtp";
        String bucket = "xiaochengxuimg";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
            return true;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return false;
        }
    }






//下面为测试用代码









    @Test
    public  void upload(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "BBJfruU970ss5X448tA8Nxk9hu8g49z6_2vjx06F";
        String secretKey = "9WCki9RQwgup0G2cNDKMd73uOfSlmi-2s9XFnQtp";
        String bucket = "xiaochengxuimg";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "D:\\image\\常用照片\\volley.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //System.out.println(putRet.key);
            //System.out.println(putRet.hash);
            System.out.println("http://q8ee90frk.bkt.clouddn.com/"+putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }


    @Test
    public void up2(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "BBJfruU970ss5X448tA8Nxk9hu8g49z6_2vjx06F";
        String secretKey = "9WCki9RQwgup0G2cNDKMd73uOfSlmi-2s9XFnQtp";
        String bucket = "xiaochengxuimg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            //File file = new File("D:\\image\\常用照片\\volley.jpg");
            FileInputStream inputStream = new FileInputStream("D:\\videos\\看.mp4");
            //byte[] uploadBytes = file.getBytes("utf-8");
            //ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                //System.out.println(putRet.key);
                //System.out.println(putRet.hash);
                System.out.println("外链是：http://q8ee90frk.bkt.clouddn.com/"+putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (FileNotFoundException ex) {
            //ignore
        }
    }

    //url中去除前面的域名http://q8ee90frk.bkt.clouddn.com/之后就是key值
    @Test
    public void deleteTest(){
        String url="http://q8ee90frk.bkt.clouddn.com/FtAV1olX_77RCzi9TAcEKijiHTPK";
        String key=url.replace("http://q8ee90frk.bkt.clouddn.com/","");//url中去除前面的域名http://q8ee90frk.bkt.clouddn.com/之后就是key值
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        String accessKey = "BBJfruU970ss5X448tA8Nxk9hu8g49z6_2vjx06F";
        String secretKey = "9WCki9RQwgup0G2cNDKMd73uOfSlmi-2s9XFnQtp";
        String bucket = "xiaochengxuimg";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
            System.out.println("删除成功！");
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }


}
