package com.taotie.wechatpro;

import com.taotie.wechatpro.dao.UserDao;
import com.taotie.wechatpro.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class WechatproApplicationTests {

    @Resource
    UserDao userDao;

    @Test
    void contextLoads() {
        List<User> users = userDao.selectList(null);
        for (User user : users) {
            System.out.println(user);
        }
    }

}
