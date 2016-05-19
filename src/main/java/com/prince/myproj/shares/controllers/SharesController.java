package com.prince.myproj.shares.controllers;

import com.prince.myproj.blog.dao.DailyDao;
import com.prince.myproj.shares.services.ShareCodeGetService;
import com.prince.myproj.shares.services.SharesHistoryDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zidong.wang on 2016/5/16.
 */
@Controller
@RequestMapping("/shares")
public class SharesController {
    public static final Logger logger = Logger.getLogger(SharesController.class);
    @Autowired
    private ShareCodeGetService shareCodeGetService;

    @Autowired
    private SharesHistoryDataService sharesHistoryDataService;

    @RequestMapping("/history")
    @ResponseBody
    public String getHistoryByCode(HttpServletRequest request,HttpServletResponse response,Model model){
        long start = Long.parseLong(request.getParameter("start"));
        long end = Long.parseLong(request.getParameter("end"));
        sharesHistoryDataService.downloadTable(start,end);
        return "history success";
        //将历史数据保存在本地
        //http://localhost:9999/shares/history?start=0&end=1
    }

    @RequestMapping("/historyXin")
    @ResponseBody
    public String getHistoryByCodeXing(HttpServletRequest request,HttpServletResponse response,Model model){
        long start = Long.parseLong(request.getParameter("start"));
        long end = Long.parseLong(request.getParameter("end"));
        sharesHistoryDataService.downloadTableContainXing(start, end);
        return "history success";
        //将加*数据保存在本地
        //http://localhost:9999/shares/historyXin?start=0&end=1
    }

    @RequestMapping("/historyToDb")
    @ResponseBody
    public String saveHistory(HttpServletRequest request,HttpServletResponse response,Model model){
        long start = Long.parseLong(request.getParameter("start"));
        long end = Long.parseLong(request.getParameter("end"));
        sharesHistoryDataService.saveTableInDB(start, end);
        return "history to db success";
        //将本地数据保存到db
        //http://localhost:9999/shares/historyToDb?start=0&end=1
    }

    @RequestMapping("/historyToday")
    @ResponseBody
    public String saveTodayHistory(HttpServletRequest request,HttpServletResponse response,Model model){
        long start = Long.parseLong(request.getParameter("start"));
        long end = Long.parseLong(request.getParameter("end"));
        String dateStart = request.getParameter("datestart");
        String dateEnd = request.getParameter("dateend");
        if(dateStart!=null&&dateStart!=null){
            sharesHistoryDataService.updateTodayHistory(start, end,dateStart,dateEnd);
        }else{
            sharesHistoryDataService.updateTodayHistory(start, end);
        }
        return "history today to db success";
        //保存今天的数据倒库中
        //http://localhost:9999/shares/historyToday?start=0&end=1&datestart=20150518&dateend=20150519
    }

    @RequestMapping("/historyTodayByCode")
    @ResponseBody
    public String saveTodayHistoryByCode(HttpServletRequest request,HttpServletResponse response,Model model){

        String codes = request.getParameter("code");
        String dateStart = request.getParameter("datestart");
        String dateEnd = request.getParameter("dateend");
        sharesHistoryDataService.updateTodayHistory(codes,dateStart,dateEnd);
        return "history today to db code success";
        //保存今天的数据倒库中
        //http://localhost:9999/shares/historyTodayByCode?code=sh000001,sz390001,sz390006&datestart=20150518&dateend=20150519
    }

    @RequestMapping("/sharesCodes")
    @ResponseBody
    public String getSharesCodes(HttpServletRequest request,HttpServletResponse response,Model model){
        shareCodeGetService.getAllCodeFromSina();
        return "success";
        //获取所有股票代码
        //http://localhost:9999/shares/sharesCodes
    }

}
