package com.prince.myproj.blog.controllers;

import com.alibaba.fastjson.JSON;
import com.prince.myproj.blog.models.ResultModel;
import com.prince.myproj.blog.services.SpiderService;
import com.prince.myproj.blog.services.TestService;
import com.prince.myproj.blog.spiders.spiderServices.TravelPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zidong.wang on 2016/1/5.
 * 伪造reffer对热门站点进行访问
 */
@Controller
@RequestMapping("/blog")
public class SpiderController {
    @Autowired
    private SpiderService spiderService;

    @Autowired
    private TravelPicService travelPicService;

    @Autowired
    private TestService testService;

    @RequestMapping("/spider/begin")
    @ResponseBody
    public String beginSpider(HttpServletRequest request,HttpServletResponse response){
        ResultModel resultModel = new ResultModel();
        try {
            spiderService.beginSpiderSite();
            resultModel.getBstatus().setCode(0);
            resultModel.setData("扫描成功");
        }catch (Exception e){
            e.printStackTrace();
            resultModel.getBstatus().setCode(-5);
            resultModel.getBstatus().setDesc("扫描失败");
        }
        return JSON.toJSONString(resultModel);
    }

    @RequestMapping("/spider/travel")
    @ResponseBody
    public String beginSpiderTravelPic(HttpServletRequest request,HttpServletResponse response){
        travelPicService.analysis();
        return "抓取百度旅行";
    }

    @RequestMapping("/spider/data")
    @ResponseBody
    public String dataAna(HttpServletRequest request,HttpServletResponse response){
//        testService.ana();
        testService.anaNode();
        return "";
    }
}
