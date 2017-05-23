package com.prince.myproj.shares.controllers;

import com.prince.myproj.shares.models.LHBCacularResult;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.myproj.shares.services.DragonTigerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
        List<LHBCacularResult> lhbCacularResults = dragonTigerService.caculateSuccessPer(date, dayNum);
        float successPer = dragonTigerService.cuculateSuccessPerByLHBResultList(lhbCacularResults);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("successPer",successPer);
        map.put("results",lhbCacularResults);
        return map;
    }

    @RequestMapping(value="/listDivisions",method = RequestMethod.GET)
    @ResponseBody
    public Object listDivisions(HttpServletRequest request){
        String date = request.getParameter("date");
        String dayNum = request.getParameter("dayNum");
        //列出效果好的营业部
        return dragonTigerService.listDivisions(date,dayNum);
    }

    @RequestMapping(value="/listDragonResultByDate",method = RequestMethod.GET)
    @ResponseBody
    public Object listDragonResultByDate(HttpServletRequest request){
        //列出龙虎榜上榜成功失败列表
        String date = request.getParameter("date");
        List<LHBCacularResult> lhbCacularResults = dragonTigerService.listDragonResultByDate(date);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("result",lhbCacularResults);
        map.put("cacularNum",lhbCacularResults.size());
        return map;
//        return null;
    }

    @RequestMapping(value="/listDragonResultOneByOne",method = RequestMethod.GET)
    @ResponseBody
    public Object listDragonResultOneByOne(HttpServletRequest request){
        String date = request.getParameter("date");
        List<LHBCacularResult> lhbCacularResults = dragonTigerService.listDragonResultOneByOne(date);

        return lhbCacularResults;
    }

    @RequestMapping(value="/simulateOp",method = RequestMethod.GET)
    @ResponseBody
    public Object simulateOp(HttpServletRequest request){
        String dateBegin = request.getParameter("dateBegin");
        String dateEnd = request.getParameter("dateEnd");
        String initMoneyStr = request.getParameter("money");
        float initMoney = 200000;
        if (initMoneyStr != null) {
            initMoney = Float.parseFloat(initMoneyStr);
        }
        return dragonTigerService.simulateOp(dateBegin,dateEnd,initMoney);//模拟操作
//        return null;
    }

}
