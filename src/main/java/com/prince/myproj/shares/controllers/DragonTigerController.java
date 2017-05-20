package com.prince.myproj.shares.controllers;

import com.alibaba.fastjson.JSON;
import com.prince.myproj.shares.models.LHBCacularResult;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.myproj.shares.models.WaveModel;
import com.prince.myproj.shares.services.DragonTigerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2017/5/18.
 */
@Controller
@RequestMapping("/shares/DragonTiger")
@ResponseBody
public class DragonTigerController {
    public static final Logger logger = Logger.getLogger(DragonTigerController.class);

    @Autowired
    private DragonTigerService dragonTigerService;

    @RequestMapping(value="/spiderDivision",method = RequestMethod.GET)
    @ResponseBody
    public String spiderDivision(HttpServletRequest request){
        dragonTigerService.spiderDivisionTask();
        return "spiderDivision ok!";
    }
    @RequestMapping(value="/spiderLHBHistory",method = RequestMethod.GET)
    @ResponseBody
    public String spiderLHBHistory(HttpServletRequest request){
        String date = request.getParameter("date");
        String dayNum = request.getParameter("dayNum");
        dragonTigerService.spiderLHBHistory(date, dayNum);
        return "spiderLHBHistory ok";
    }

    @RequestMapping(value="/spiderLHBToday",method = RequestMethod.GET)
    @ResponseBody
    public String spiderLHBToday(HttpServletRequest request){
        dragonTigerService.spiderLHBToday();
        return "spiderLHBToday ok";
    }

    @RequestMapping(value="/caculateByLHB",method = RequestMethod.GET)
    @ResponseBody
    public Object caculateByLHB(HttpServletRequest request){
        String date = request.getParameter("date");
        List<SharesSingleModel> singleModels = dragonTigerService.caculateByLHB(date);
        return singleModels;
    }

    @RequestMapping(value="/validateCaculateByLHB",method = RequestMethod.GET)
    @ResponseBody
    public Object validateCaculateByLHB(HttpServletRequest request){
        String date = request.getParameter("date");
        List<LHBCacularResult> lhbCacularResults = dragonTigerService.validateCaculateByLHB(date);
        return  lhbCacularResults;
    }
    @RequestMapping(value="/caculateSuccessPer",method = RequestMethod.GET)
    @ResponseBody
    public Object caculateSuccessPer(HttpServletRequest request){
        String date = request.getParameter("date");
        String dayNum = request.getParameter("dayNum");
        float successPer = dragonTigerService.caculateSuccessPer(date,dayNum);
        return "算法成功率："+successPer;
    }

    @RequestMapping(value="/listDivisions",method = RequestMethod.GET)
    @ResponseBody
    public Object listDivisions(HttpServletRequest request){
        String date = request.getParameter("date");
        String dayNum = request.getParameter("dayNum");
        //列出效果好的营业部
        return dragonTigerService.listDivisions(date,dayNum);
    }

}
