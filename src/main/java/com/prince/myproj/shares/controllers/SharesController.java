package com.prince.myproj.shares.controllers;

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
        //http://localhost:9999/shares/history?start=0&end=1
    }

    @RequestMapping("/historyXin")
    @ResponseBody
    public String getHistoryByCodeXing(HttpServletRequest request,HttpServletResponse response,Model model){
        long start = Long.parseLong(request.getParameter("start"));
        long end = Long.parseLong(request.getParameter("end"));
        sharesHistoryDataService.downloadTableContainXing(start, end);
        return "history success";
        //http://localhost:9999/shares/historyXin?start=0&end=1
    }

    @RequestMapping("/historyToDb")
    @ResponseBody
    public String saveHistory(HttpServletRequest request,HttpServletResponse response,Model model){
        long start = Long.parseLong(request.getParameter("start"));
        long end = Long.parseLong(request.getParameter("end"));
        sharesHistoryDataService.saveTableInDB(start, end);
        return "history to db success";
        //http://localhost:9999/shares/historyToDb?start=0&end=1
    }

    @RequestMapping("/historyToday")
    @ResponseBody
    public String saveTodayHistory(HttpServletRequest request,HttpServletResponse response,Model model){
        long start = Long.parseLong(request.getParameter("start"));
        long end = Long.parseLong(request.getParameter("end"));
        sharesHistoryDataService.updateTodayHistory(start, end);
        return "history today to db success";
        //http://localhost:9999/shares/historyToday?start=0&end=1
    }

    @RequestMapping("/sharesCodes")
    @ResponseBody
    public String getSharesCodes(HttpServletRequest request,HttpServletResponse response,Model model){
        shareCodeGetService.getAllCodeFromSina();
        return "success";
        //http://localhost:9999/shares/sharesCodes
    }

}
