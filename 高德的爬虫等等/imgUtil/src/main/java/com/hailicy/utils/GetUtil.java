package com.hailicy.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * 创建时间: 2020/4/20 14:32
 * 文件备注:
 * 编写人员:
 */


public class GetUtil extends HttpServlet {

    public static String SendGET(String url,String param){
        String result="";//访问返回结果
        BufferedReader read=null;//读取访问结果

        try {
            //创建url
            URL realurl=new URL(url+"?"+param);
            //打开连接
            URLConnection connection=realurl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //建立连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段，获取到cookies等
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            read = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;//循环读取
            while ((line = read.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(read!=null){//关闭流
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Test
    public void test(){
        String url="https://restapi.amap.com/v3/place/polygon?key=76a97d33ead4b59316114d1514a62779&polygon=114.474792,30.430617|114.308624,30.566695&keywords=美食|餐饮";
        String param = null;
        String ans = SendGET(url,param);
        JSONObject json = JSONObject.parseObject(ans);

        System.out.println(json.toString());
        String aaaa = json.get("pois").toString();
        int i=1;
        jsonSaveUtil.saveDataToFile("D:\\notes\\json\\"+i+".json",aaaa);
    }

    @Test
    public void test2(){
        String param = null;
        String ans=null;
        JSONObject jsonObject=null;
        //初始坐标
        //Double x1 = 114.131469;
        Double x1 = 109.072265;
        //Double x2 = 114.586715;
        //Double y1 = 30.695201;
        Double y1 = 33.027087;
        //Double y2 = 30.442162;
        String aaaa=null;
        String temp=null;
        //替换xyxyxyx
        int xuhao=1;
        StringBuffer url=new StringBuffer("https://restapi.amap.com/v3/place/polygon?key=76a97d33ead4b59316114d1514a62779&polygon=---&keywords=美食|餐饮");
        for(int i=0;i<30;i++){
            for (int j=0;j<70;j++){
                url = new StringBuffer("https://restapi.amap.com/v3/place/polygon?key=76a97d33ead4b59316114d1514a62779&polygon=---&keywords=美食|餐饮");
                temp = url.replace(87,90,x1+","+y1+"|"+(x1+0.095424)+","+(y1-0.101136)).toString();
                System.out.println(url);
                ans = SendGET(temp,param);
                jsonObject =  JSONObject.parseObject(ans);
                aaaa = jsonObject.get("pois").toString();
                jsonSaveUtil.saveDataToFile("D:\\notes\\json\\"+xuhao+".json",aaaa);
                //每次序号加一
                xuhao = xuhao+1;
                System.out.println("到"+xuhao);
                y1-=0.0253039;
            }
            x1+=0.00455246;
            y1=30.695201;
        }
        System.out.println("完成");


    }

}
