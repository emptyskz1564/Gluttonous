package com.taotie.wechatpro.controller.set;


import com.taotie.wechatpro.service.impl.DiscussServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1")
public class DiscussController {

    @Autowired
    DiscussServiceImpl discussService;

    /**
     * 得到评论的上传json
     * @param str
     */
    @ResponseBody
    @RequestMapping(value = "/discuss",method = RequestMethod.POST)
    public Integer upDis(@RequestParam@RequestBody String str){
        discussService.upDis(str);
        return 1;
    }
}
