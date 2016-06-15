package com.prince.myproj.shares.controllers;

import com.alibaba.fastjson.JSON;
import com.prince.myproj.blog.models.ResultModel;
import com.prince.myproj.shares.services.CheckSharesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zidong.wang on 2016/6/15.
 */
@Controller
@RequestMapping("/shares")
public class CheckSharesController {
    public static final Logger logger = Logger.getLogger(CheckSharesController.class);

    @Autowired
    private CheckSharesService checkSharesService;
    @RequestMapping("/beginCheck")
    @ResponseBody
    public String beginCheck(HttpServletRequest request,HttpServletResponse response,Model model){
        String on = request.getParameter("on");
        if("0".equals(on)){
            checkSharesService.onOffTask(false);
        }else{
            checkSharesService.onOffTask(true);
        }
        ResultModel resultModel = new ResultModel();

        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("检测开启");
        return JSON.toJSONString(resultModel);
    }

    @RequestMapping("/checkShares")
    @ResponseBody
    public String checkShares(HttpServletRequest request,HttpServletResponse response,Model model){
        String check = request.getParameter("check");
        String code = request.getParameter("code");
        String minPrice = request.getParameter("min");
        String maxPrice = request.getParameter("max");

        int type = Integer.parseInt(check);
        float min = -1;
        float max = 10000;
        if(minPrice!=null){
            min = Float.parseFloat(minPrice);
        }
        if(maxPrice != null){
            max = Float.parseFloat(maxPrice);
        }

        checkSharesService.ctrlCheck(type,max,min,code);

        ResultModel resultModel = new ResultModel();

        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("检测开始");
        return JSON.toJSONString(resultModel);
    }
}
