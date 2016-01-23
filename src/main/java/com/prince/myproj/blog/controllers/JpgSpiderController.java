package com.prince.myproj.blog.controllers;

import com.prince.myproj.blog.spiders.spiderServices.SpiderXiaohuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gagaprince on 15-12-26.
 */
@Controller
@RequestMapping("/blog")
public class JpgSpiderController {
    @Autowired
    private SpiderXiaohuaService spiderService;

    @RequestMapping("/jpgSpider")
    @ResponseBody
    public String startSpiderJPG(){

        spiderService.startSpider();

        return "success";
    }
    @RequestMapping("/jpgUpload")
    @ResponseBody
    public String startUpload(){
        spiderService.uploadJpg();
        return "success";
    }
}
