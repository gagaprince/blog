package com.prince.myproj.blog.controllers;

import com.alibaba.fastjson.JSON;
import com.prince.myproj.blog.models.DailyModel;
import com.prince.myproj.blog.models.ResultModel;
import com.prince.myproj.blog.services.SpiderService;
import com.prince.myproj.blog.services.TestService;
import com.prince.myproj.blog.spiders.spiderServices.TravelPicService;
import com.prince.myproj.platform.common.models.AjaxModel;
import org.apache.log4j.Logger;
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
    public static final Logger logger = Logger.getLogger(SpiderController.class);

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

    @RequestMapping("/spider/jianshu")
    @ResponseBody
    public AjaxModel spiderJianShu(HttpServletRequest request,HttpServletResponse response){

        String url = request.getParameter("url");
        String cate = request.getParameter("cate");
        String bigCate = request.getParameter("bigCate");
        String tag = request.getParameter("tag");

//        logger.info(cate);
//        logger.info(bigCate);
//        logger.info(tag);

        DailyModel dailyModel = new DailyModel();
        dailyModel.setCate(cate);
        dailyModel.setBigCate(bigCate);
        dailyModel.setTag(tag);

        AjaxModel ajaxModel = null;
        if(url.indexOf("blog.csdn")!=-1){
            ajaxModel = spiderService.spiderCsdn(url,dailyModel);
        }else{
            ajaxModel = spiderService.spiderJianShu(url,dailyModel);
        }
        return ajaxModel;
    }
}
