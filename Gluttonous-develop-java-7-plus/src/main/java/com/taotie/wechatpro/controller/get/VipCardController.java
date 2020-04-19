package com.taotie.wechatpro.controller.get;


import com.taotie.wechatpro.dao.CardDao;
import com.taotie.wechatpro.pojo.Card;
import com.taotie.wechatpro.pojo.User;
import com.taotie.wechatpro.pojo.association.CardLable;
import com.taotie.wechatpro.pojo.association.UserLable;
import com.taotie.wechatpro.pojo.association.UserRestaurant;
import com.taotie.wechatpro.pojo.view.VCardUserLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/v1")
public class VipCardController {

    @Autowired
    CardDao cardDao;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @ResponseBody
    @RequestMapping(value = "/vipcards/{id}",method = RequestMethod.GET)
    public Object getCard(@PathVariable Integer id){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        //假装登录（test完成，删除）
//        User user = new User();
//        user.setUserId(1);
//        user.setVip(1);
//        redisTemplate.opsForValue().set("User_userId:"+id,user);

        //此处通过传来的id(userid)得到redis对应的user缓存
        User user1 = (User) redisTemplate.opsForValue().get("User_userId:"+id);
        Integer vip = user1.getVip();

        //对是否vip进行判断如果是则开始推荐功能，否则返回“丢你雷姆，充钱去啊”
        if (vip == 1){
            //要被推荐出去的cardId的数组
            List<Integer> cardIdlist1 = new ArrayList<>();



            //根据点赞打卡的餐厅推荐（这个地方没有进行sql操作，因为是一个流程下来的，肯定是先点赞打卡存入redis才能再推荐的）
            List<VCardUserLike> list1 = (List<VCardUserLike>) redisTemplate.opsForValue().get("VCardUserLikes_userId"+id);

            //对vcarduserlike的数组进行遍历获取cardid得到cardid数组
            if (list1 != null) {
                for (int k = list1.size()-1; k > 0; k--) {
                    cardIdlist1.add(list1.get(k).getCardId());
                }
            }



            //根据收藏餐厅推荐（这个地方也没进行sql操作，因为用户不收藏餐厅无法进行推荐）
            List<UserRestaurant> list2 = (List<UserRestaurant>) redisTemplate.opsForValue().get("UserRestaurant_userId:"+id);

            //对UserRestaurant的数组进行遍历得到对应的resId再操作resId得到cardId
            //通过每次上传打卡储存的redis中的allCard得到card数组
            List<Card> allcardList = (List<Card>) redisTemplate.opsForValue().get("allCard");

            if (list2 != null && allcardList != null) {
                for (int k = list2.size()-1; k > 0; k--) {
                    Integer resId = list2.get(k).getResId();
                    for (int i = allcardList.size()-1; i > 0; i--) {
                        if (allcardList.get(i).getResId() == resId) {
                            cardIdlist1.add(allcardList.get(i).getCardId());
                        }
                    }
                }
            }


            //根据个人口味标签推荐
            //从upchangeUser方法中存储的redis中得到userlable的数组
            List<UserLable> userlablelist = (List<UserLable>) redisTemplate.opsForValue().get("UserLable_userId:"+id);

            //对userlablelist进行遍历，并得到lableid再操作
            if (userlablelist != null) {
                for (int k = userlablelist.size()-1; k > 0; k--) {
                    Integer lableId = userlablelist.get(k).getLableId();
                    List<CardLable> cardlablelist = (List<CardLable>) redisTemplate.opsForValue().get("CardLable_lableId:" + lableId);
                    if (cardlablelist != null) {
                        for (int i = cardlablelist.size()-1; i > 0; i--) {
                            cardIdlist1.add(cardlablelist.get(i).getCardId());
                        }
                    }
                }
            }


            //将得到的cardIdlist1，转为card的数组并返回
            List<Card> cardList = new ArrayList<>();
            if (cardIdlist1 != null) {
                for (int k = cardIdlist1.size()-1; k > 0; k--) {
                    cardList.add(cardDao.selectById(cardIdlist1.get(k)));
                }
            }

            if (cardList.size() == 0) {
                return -1;
            }else {
                return cardList;
            }
        }else{
            //返回没有推荐
            return -2;
        }
    }
}
