package com.prince.myproj.shares.controllers;

import com.alibaba.fastjson.JSON;
import com.prince.myproj.blog.dao.DailyDao;
import com.prince.myproj.blog.models.ResultModel;
import com.prince.myproj.shares.models.AnalysisBean;
import com.prince.myproj.shares.models.AnalysisBuyTimeBean;
import com.prince.myproj.shares.models.AnalysisBuyTimeTotal;
import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.services.ShareAnalysisService;
import com.prince.myproj.shares.services.ShareCodeGetService;
import com.prince.myproj.shares.services.SharesHistoryDataService;
import com.prince.myproj.shares.services.SharesMailService;
import com.prince.myproj.util.DateUtil;
import com.prince.myproj.util.MailService;
import com.prince.myproj.util.bean.Mail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private DateUtil dateUtil;

    @Autowired
    private SharesMailService sharesMailService;
    @Autowired
    private ShareAnalysisService shareAnalysisService;

    @Autowired
    private SharesHistoryDataService sharesHistoryDataService;

    @RequestMapping("/today")
    public String viewTodayShares(HttpServletRequest request,HttpServletResponse response,Model model){

        String shareDate = request.getParameter("date");
        List<SharesModel> zhiModels = shareAnalysisService.getZhiModels();
        if(shareDate==null){
            shareDate = zhiModels.get(0).getDate();
        }

        List<SharesModel> highModels = shareAnalysisService.findIncreaseHigherList(8, shareDate);
        List<SharesModel> lowModels = shareAnalysisService.findIncreaseLowerList(-8, shareDate);
        List<SharesModel> cys5LowModels = shareAnalysisService.findCysList("cys5low", -10, shareDate);
        List<SharesModel> cys13LowModels = shareAnalysisService.findCysList("cys13low", -16, shareDate);
        List<SharesModel> cys34LowModels = shareAnalysisService.findCysList("cys34low",-20,shareDate);
        List<SharesModel> cys5HighModels = shareAnalysisService.findCysList("cys5high", 10, shareDate);
        List<SharesModel> cys13HighModels = shareAnalysisService.findCysList("cys13high", 16, shareDate);
        List<SharesModel> cys34HighModels = shareAnalysisService.findCysList("cys34high", 20, shareDate);

        model.addAttribute("zhiModels",zhiModels);
        model.addAttribute("highModels",highModels);
        model.addAttribute("lowModels",lowModels);
        model.addAttribute("shareDate",shareDate);
        model.addAttribute("cys5LowModels",cys5LowModels);
        model.addAttribute("cys13LowModels",cys13LowModels);
        model.addAttribute("cys34LowModels",cys34LowModels);
        model.addAttribute("cys5HighModels",cys5HighModels);
        model.addAttribute("cys13HighModels",cys13HighModels);
        model.addAttribute("cys34HighModels",cys34HighModels);
        model.addAttribute("codeMap",sharesHistoryDataService.getCodeNameMap());

        return "shares/todayShares";
    }

    @RequestMapping("/preToday")
    public String viewPreTodayShares(HttpServletRequest request,HttpServletResponse response, Model model){

        String shareDate = dateUtil.getNowDate("yyyy-MM-dd");

        List<SharesModel> cys5LowModels = shareAnalysisService.findCysPreList("cys5low", -10);
        List<SharesModel> cys13LowModels = shareAnalysisService.findCysPreList("cys13low", -16);
        List<SharesModel> cys34LowModels = shareAnalysisService.findCysPreList("cys34low", -20);
        List<SharesModel> cys5HighModels = shareAnalysisService.findCysPreList("cys5high", 10);
        List<SharesModel> cys13HighModels = shareAnalysisService.findCysPreList("cys13high", 16);
        List<SharesModel> cys34HighModels = shareAnalysisService.findCysPreList("cys34high",20);

        model.addAttribute("shareDate",shareDate);
        model.addAttribute("cys5LowModels",cys5LowModels);
        model.addAttribute("cys13LowModels",cys13LowModels);
        model.addAttribute("cys34LowModels",cys34LowModels);
        model.addAttribute("cys5HighModels",cys5HighModels);
        model.addAttribute("cys13HighModels",cys13HighModels);
        model.addAttribute("cys34HighModels",cys34HighModels);
        model.addAttribute("codeMap",sharesHistoryDataService.getCodeNameMap());

        return "shares/preTodayShares";
    }

    @RequestMapping("/analysisByDay")
    public String analysisByDay(HttpServletRequest request,HttpServletResponse response,Model model){
        String shareDate = request.getParameter("date");
        if(shareDate == null){
            shareDate = dateUtil.getNowDate("yyyy-MM-dd");
        }

        List<SharesModel> cys13LowModels = shareAnalysisService.findCysList("cys13low", -16, shareDate);

        Map<String,List<SharesModel>> lowCysMap = shareAnalysisService.findLowCysSomeDayData(cys13LowModels, 10);

        model.addAttribute("shareDate",shareDate);
        model.addAttribute("cys13LowModels",cys13LowModels);
        model.addAttribute("lowcysMap",lowCysMap);

        return "shares/analysisByDay";
    }

    @RequestMapping("/analysisByCode")
    public String analysisByCode(HttpServletRequest request,HttpServletResponse response,Model model){
        String codestr = request.getParameter("codes");
        int day = Integer.parseInt(request.getParameter("day"));
        if(codestr == null){
            codestr = "sh000001";
        }

        String[] codes = codestr.split(",");


        Map<String,List<SharesModel>> cysModelListMap = shareAnalysisService.giveMeCysModelList(codes, day);

        Map<String,AnalysisBean> analysisBeanMap = shareAnalysisService.findLowestCysAndT(codes, cysModelListMap);

        model.addAttribute("cysModelListMap", cysModelListMap);
        model.addAttribute("analysisBeanMap",analysisBeanMap);

        return "shares/analysisByCode";
    }

    @RequestMapping("/history")
    @ResponseBody
    public String getHistoryByCode(HttpServletRequest request,HttpServletResponse response,Model model){
        long start = Long.parseLong(request.getParameter("start"));
        long end = Long.parseLong(request.getParameter("end"));
        sharesHistoryDataService.downloadTable(start, end);
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
//            sharesHistoryDataService.updateTodayHistory(start, end);
        }
        return "history today to db success";
        //保存今天的数据倒库中
        //http://localhost:9999/shares/historyToday?start=0&end=1&datestart=20150518&dateend=20150519
    }
    @RequestMapping("/saveHistoryData")
    @ResponseBody
    public String saveHistoryData(HttpServletRequest request,HttpServletResponse response,Model model){
        ResultModel resultModel = new ResultModel();
        sharesHistoryDataService.downloadTable();
        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("保存数据完毕");
        return JSON.toJSONString(resultModel);
    }

    @RequestMapping("/historyTodayByCode")
    @ResponseBody
    public String saveTodayHistoryByCode(HttpServletRequest request,HttpServletResponse response,Model model){
        ResultModel resultModel = new ResultModel();
        String codes = request.getParameter("code");
        String dateStart = request.getParameter("datestart");
        String dateEnd = request.getParameter("dateend");
        sharesHistoryDataService.updateTodayHistory(codes,dateStart,dateEnd);

        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("history today to db code success");

        return JSON.toJSONString(resultModel);
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


    @RequestMapping("/cacularMeans")
    @ResponseBody
    public String cacularMeans(HttpServletRequest request,HttpServletResponse response,Model model){
        long start = Long.parseLong(request.getParameter("start"));
        long end = Long.parseLong(request.getParameter("end"));
        String dateStart = request.getParameter("datestart");
        String dateEnd = request.getParameter("dateend");
        sharesHistoryDataService.cacularMean(start, end, dateStart, dateEnd);
        return "cacularMeans  success";
        //保存今天的数据倒库中
        //http://localhost:9999/shares/cacularMeans?start=0&end=1&datestart=19491001&dateend=20160520
    }
    @RequestMapping("/cacularAllMeans")
    @ResponseBody
    public String cacularAllMeans(HttpServletRequest request,HttpServletResponse response,Model model){
        ResultModel resultModel = new ResultModel();

        sharesHistoryDataService.cacularMean();

        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("计算平均完成");
        return JSON.toJSONString(resultModel);
    }

    @RequestMapping("/sendMail")
    @ResponseBody
    public String sendMail(HttpServletRequest request,HttpServletResponse response,Model model){
        ResultModel resultModel = new ResultModel();

//        sharesMailService.sendMail();
        sharesMailService.sendMailPre();
        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("邮件发送完成");
        return JSON.toJSONString(resultModel);

    }

    @RequestMapping("/cacularAllCyc")
    @ResponseBody
    public String cacularAllCyc(HttpServletRequest request,HttpServletResponse response,Model model){
        ResultModel resultModel = new ResultModel();

        sharesHistoryDataService.cacularCycHistory();

        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("计算所有平均成本完成");
        return JSON.toJSONString(resultModel);
    }

    @RequestMapping("/cacularLastCyc")
    @ResponseBody
    public String cacularLastCyc(HttpServletRequest request,HttpServletResponse response,Model model){
        ResultModel resultModel = new ResultModel();

        sharesHistoryDataService.cacularCycLastDay();

        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("计算最后一日成本完成");
        return JSON.toJSONString(resultModel);
    }

    @RequestMapping("/modifyCyc")
    @ResponseBody
    public String modifyCyc(HttpServletRequest request,HttpServletResponse response,Model model){
        ResultModel resultModel = new ResultModel();

        sharesHistoryDataService.modifyCacularCycHistory();

        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("修复所有平均成本完成");
        return JSON.toJSONString(resultModel);
    }

    @RequestMapping("/cacuAll")
    @ResponseBody
    public String kingKey(HttpServletRequest request,HttpServletResponse response,Model model){
        ResultModel resultModel = new ResultModel();

        sharesHistoryDataService.downloadTable();
        sharesHistoryDataService.cacularMean();
        sharesHistoryDataService.cacularCycLastDay();
//        sharesMailService.sendMail();
//        sharesMailService.sendMailBuyShares();

        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("上述过程已经完成");
        return JSON.toJSONString(resultModel);
    }
    @RequestMapping("/cacuAllPre")
    @ResponseBody
    public String cacuAllPre(HttpServletRequest request,HttpServletResponse response,Model model){
        long timeStart = new Date().getTime();

        //在不到三点时获取股票数据并进行 选股操作 发出邮件
        ResultModel resultModel = new ResultModel();

        sharesHistoryDataService.downloadTablePre();
//        sharesHistoryDataService.cacularMean();
        sharesHistoryDataService.cacularCycLastDayPre();
        sharesMailService.sendMailPre();
        sharesMailService.sendMailPreBuyShares();
        long timeEnd = new Date().getTime();

        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("预测完成 用时："+(timeEnd-timeStart)/1000);

        return JSON.toJSONString(resultModel);
    }
    @RequestMapping("/analysisBuyTime")
    public String analysisBuyTime(HttpServletRequest request,HttpServletResponse response,Model model){

        String dayStr = request.getParameter("day");
        String waitDayStr = request.getParameter("waitday");
        String incStr = request.getParameter("inc");
        int day = 60;
        int waitDay = 100;
        float inc = 0.1f;
        if(dayStr!=null){
            day = Integer.parseInt(dayStr);
        }
        if(waitDayStr!=null){
            waitDay = Integer.parseInt(waitDayStr);
        }
        if(incStr!=null){
            inc = Float.parseFloat(incStr);
        }

        List<AnalysisBuyTimeBean> analysisBuyTimeBeanList = shareAnalysisService.analysisBuyTime(day, waitDay, inc);

        model.addAttribute("buyTimeList", analysisBuyTimeBeanList);
        model.addAttribute("day", day);

        return "shares/buyTime";
    }
    @RequestMapping("/analysisBuyShares")
    public String analysisBuyShares(HttpServletRequest request,HttpServletResponse response,Model model){
        String date = request.getParameter("date");
        String dayStr = request.getParameter("day");
        String waitDayStr = request.getParameter("waitday");
        String incStr = request.getParameter("inc");
        String lowCys = request.getParameter("cys");
        String isPre = request.getParameter("isPre");
        int day = 60;
        int waitDay = 100;
        float inc = 0.1f;
        float cys=-20;
        if(isPre == null){
            isPre = "normal";
        }
        if(lowCys!=null){
            cys = Float.parseFloat(lowCys);
        }
        if(dayStr!=null){
            day = Integer.parseInt(dayStr);
        }
        if(waitDayStr!=null){
            waitDay = Integer.parseInt(waitDayStr);
        }
        if(incStr!=null){
            inc = Float.parseFloat(incStr);
        }
        if(date == null){
            date = sharesHistoryDataService.giveMeLastDate();
        }

        List<SharesModel> cacularShares = null;
        if(isPre.equals("normal")){
            cacularShares = shareAnalysisService.cacularBuyShares(date,day,cys);
        }else{
            cacularShares = shareAnalysisService.cacularBuySharesPre(date,day,cys);
        }


        model.addAttribute("cacularShares",cacularShares);
        model.addAttribute("codeMap",sharesHistoryDataService.getCodeNameMap());
        model.addAttribute("date",date);

        AnalysisBuyTimeTotal analysisBuyTimeTotal = shareAnalysisService.testRealInc(cacularShares, waitDay, inc);
        List<AnalysisBuyTimeBean> analysisBuyTimeBeanList = analysisBuyTimeTotal.getAnalysisBuyTimeBeanList();

        model.addAttribute("analysisTotal",analysisBuyTimeTotal);
        model.addAttribute("buyTimeList", analysisBuyTimeBeanList);
        model.addAttribute("day", day);

        return "shares/cacularBuyShares";
    }


    @RequestMapping("/cacularBuyShares")
    @ResponseBody
    public String cacularBuyShares(HttpServletRequest request,HttpServletResponse response,Model model){

        ResultModel resultModel = new ResultModel();
        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("结果已经发送邮件");
        return JSON.toJSONString(resultModel);
    }
    @RequestMapping("/tjCacularBuySHares")
    @ResponseBody
    public String tjCacularBuySHares(HttpServletRequest request,HttpServletResponse response,Model model){
        ResultModel resultModel = new ResultModel();

        List<AnalysisBuyTimeTotal> analysisBuyTimeTotalList = new ArrayList<AnalysisBuyTimeTotal>();
        int allNum = 0;
        int successNum = 0;
        int fallNum = 0;
        int incNum = 0;

        for(int i=0;i<30;i++){
            String date = dateUtil.getAddDate("2016-06-03","yyyy-MM-dd",-24*60*60*1000*i);
            List<SharesModel> cacularShares = shareAnalysisService.cacularBuyShares(date, 60, -20);
            AnalysisBuyTimeTotal analysisBuyTimeTotal = shareAnalysisService.testRealInc(cacularShares, 3, 0.04f);
            analysisBuyTimeTotalList.add(analysisBuyTimeTotal);

            allNum += analysisBuyTimeTotal.getSuccessNum();
            successNum += analysisBuyTimeTotal.getSuccessNum();
            allNum += analysisBuyTimeTotal.getFallNum();
            fallNum += analysisBuyTimeTotal.getFallNum();
            incNum += analysisBuyTimeTotal.getIncreaseNum();


        }
        logger.info("总样本数:"+allNum);
        logger.info("总成功数:" + successNum + "---总失败数：" + fallNum+"---预测收涨股:"+incNum);



        resultModel.getBstatus().setCode(0);
        resultModel.getBstatus().setDesc("统计结束");
        return JSON.toJSONString(resultModel);
    }



}
