package com.prince.myproj.shares.controllers;

import com.prince.myproj.shares.models.AnalysisBuyTimeTotal;
import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.services.SelectSharesService;
import com.prince.myproj.shares.services.ShareAnalysisService;
import com.prince.myproj.util.DateUtil;
import com.prince.myproj.util.ParseUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2016/8/1.
 */
@Controller
@RequestMapping("/shares")
public class SelectSharesController {
    public static final Logger logger = Logger.getLogger(SelectSharesController.class);

    @Autowired
    private ParseUtil parseUtil;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private SelectSharesService selectSharesService;

    @Autowired
    private ShareAnalysisService shareAnalysisService;

    @RequestMapping("/select")
    public String selectShares(HttpServletRequest request,HttpServletResponse response,Model model){
        int type = parseUtil.parseInt(request.getParameter("type"), 0);
        float inc = parseUtil.parseFloat(request.getParameter("inc"), 0.05f);
        String date = parseUtil.parseString(request.getParameter("date"),dateUtil.getNowDate("yyyy-MM-dd"));
        int waitDay = 5;

        Map<String,List<SharesModel>> cacularSharesMap = null;

        if(type==0){
            //duan qi
            cacularSharesMap = selectSharesService.selectSharesList(date);
            waitDay=10;
        }else{
            waitDay=15;
        }
        Map<String,AnalysisBuyTimeTotal> resultMap=null;
        if(cacularSharesMap!=null){
            resultMap = shareAnalysisService.testRealInc(cacularSharesMap, waitDay, inc);
        }

        model.addAttribute("resultMap",resultMap);


        return "shares/select";
    }

}
