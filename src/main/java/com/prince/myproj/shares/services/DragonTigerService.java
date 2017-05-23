package com.prince.myproj.shares.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prince.myproj.shares.dao.DivisionDao;
import com.prince.myproj.shares.dao.DragonTigerDao;
import com.prince.myproj.shares.dao.SharesDao;
import com.prince.myproj.shares.dao.SharesHistoryDao;
import com.prince.myproj.shares.models.*;
import com.prince.myproj.util.DateUtil;
import com.prince.myproj.weixin.bean.ShareModel;
import com.prince.util.httputil.HttpUtil;
import org.apache.commons.codec.StringDecoder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by zidong.wang on 2017/5/18.
 */
@Service
public class DragonTigerService {

    public static final Logger logger = Logger.getLogger(DragonTigerService.class);

    @Autowired
    private ShareConfig config;

    @Autowired
    private DivisionDao divisionDao;
    @Autowired
    private SharesDao sharesDao;
    @Autowired
    private SharesHistoryDao sharesHistoryDao;

    @Autowired
    private DragonTigerDao dragonTigerDao;

    private DateUtil dateUtil = new DateUtil();

    public void spiderDivisionTask(){
        logger.info("开始抓取division 信息：");

        List<Map<String,Object>> companyMaps = getCompanys();

        for(int i=0;i<companyMaps.size();i++){
            Map<String,Object> company = companyMaps.get(i);
            logger.info(company.get("name"));
            List<DivisionBean> divisionBeans = getDivisionByCompanys(company);
            for(int j=0;j<divisionBeans.size();j++){
                DivisionBean divisionBean = divisionBeans.get(j);
                if(this.isExsitInDivisionDb(divisionBean)){
                    logger.info(divisionBean.getName()+" 已经存在");
                }else {
                    logger.info("保存 "+divisionBean.getName()+" "+divisionBean.getProvince()+" "+divisionBean.getCode()+" "+divisionBean.getCompany());
                    divisionDao.save(divisionBean);
                }

            }
        }

    }
    private boolean isExsitInDivisionDb(DivisionBean divisionBean){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code", divisionBean.getCode());
        DivisionBean divisionSelect = divisionDao.getDivisionByCode(map);
        if(divisionSelect!=null){
            return true;
        }
        return false;

    }

    private List<Map<String,Object>> getCompanys(){

        List<Map<String,Object>> companys = new ArrayList<Map<String, Object>>();

        HttpUtil httpUtil = HttpUtil.getInstance();
        String htmlContent = httpUtil.getContentByUrl(config.getDivisionAllUrl());
//        logger.info(htmlContent);

        Document doc = Jsoup.parse(htmlContent);

        Element content = doc.getElementsByClass("company-container").get(0);
        Elements companyEles = content.getElementsByTag("a");
        for(int i=0;i<companyEles.size();i++){
            Element companyEle = companyEles.get(i);

            String name = companyEle.html();
            String link = companyEle.attr("href");

            String companyCode = link.substring(link.lastIndexOf('/') + 1, link.lastIndexOf('.'));

            Map<String,Object> companyMap = new HashMap<String, Object>();
            companyMap.put("name", name);
            companyMap.put("code", companyCode);

            companys.add(companyMap);

        }

        return companys;
    }

    private List<DivisionBean> getDivisionByCompanys(Map<String,Object> company){
        List<DivisionBean> divisionBeans = new ArrayList<DivisionBean>();


        String code = (String)company.get("code");
        String baseSearchUrl = config.getDivisionSearchUrl();
        baseSearchUrl += "?pagesize=3000&page=1&param=&sortRule=-1&sortType=UpCount&typeCode=1&code="+code;
        logger.info(baseSearchUrl);
        HttpUtil httpUtil = HttpUtil.getInstance();
        String jsonContent = httpUtil.getContentByUrl(baseSearchUrl);
//        logger.info(jsonContent);

        JSONObject dataJson = JSON.parseObject(jsonContent);
        JSONArray jsonDivisionArray = dataJson.getJSONArray("data");

        for(int i=0;i<jsonDivisionArray.size();i++){
            JSONObject jsonDivision = jsonDivisionArray.getJSONObject(i);
            DivisionBean divisionBean = new DivisionBean();
            divisionBean.setName(jsonDivision.getString("SalesName"));
            divisionBean.setCode(jsonDivision.getInteger("SalesCode"));
            divisionBean.setProvince(jsonDivision.getString("Province"));
            divisionBean.setCompany((String) company.get("name"));
            divisionBeans.add(divisionBean);
        }
        return divisionBeans;
    }

    public void spiderLHBHistory(String date,String dayNum){
        if(date==null){
            date = dateUtil.getNowDate("yyyy-MM-dd");
        }
        int dayNumRe = 150;
        if(dayNum!=null){
            dayNumRe = Integer.parseInt(dayNum);
        }
        String nowDate = date;
        for(int i=1;i<dayNumRe;i++){
            String searchDate = dateUtil.getAddDate(nowDate, "yyyy-MM-dd", -24L * 3600 * 1000 * i);
            spiderLBHByDate(searchDate);
        }
    }

    public void spiderLHBToday(){
        String nowDate = dateUtil.getNowDate("yyyy-MM-dd");
        spiderLBHByDate(nowDate);
    }

    private void spiderLBHByDate(String date){
        String listUrl = config.getLhbListUrl();
        listUrl += "pagesize=200,page=1,sortRule=-1,sortType=,startDate="+date+",endDate="+date+",gpfw=0,js=.html?rt=24918370";
        logger.info(listUrl);
        List<DragonTigerBean> dragonTigerBeans = getDragonListFromListUrl(listUrl, date);

        for (int i=0;i<dragonTigerBeans.size();i++){
            DragonTigerBean dragonTigerBean = dragonTigerBeans.get(i);
            try{
                parseDragonDetail(dragonTigerBean);
            }catch (Exception e){
                e.printStackTrace();
            }



            logger.info("保存 " + dragonTigerBean.getShareName() + " buy1Division:" + dragonTigerBean.getBuy1Division() + " buy1val:" + dragonTigerBean.getBuy1Val());

            //保存
            if(!isExsitInDragonTigerDb(dragonTigerBean)){
                dragonTigerDao.save(dragonTigerBean);
            }
        }


    }

    private List<DragonTigerBean> getDragonListFromListUrl(String listUrl,String date){
        List<DragonTigerBean> dragonTigerBeanList = new ArrayList<DragonTigerBean>();

        HttpUtil httpUtil = HttpUtil.getInstance();
        String jsonHtml = httpUtil.getContentByUrl(listUrl);
//        logger.info(jsonHtml);

        JSONObject jsonDragonData  = JSON.parseObject(jsonHtml);
        JSONArray jsonDragonList = jsonDragonData.getJSONArray("data");


        for(int i=0;i<jsonDragonList.size();i++){
            JSONObject jsonDragon = jsonDragonList.getJSONObject(i);
            DragonTigerBean dragonTigerBean = new DragonTigerBean();
            dragonTigerBean.setCurrentDate(date);
            dragonTigerBean.setShareCode(jsonDragon.getString("SCode"));
            dragonTigerBean.setShareName(jsonDragon.getString("SName"));
            dragonTigerBean.setReason(jsonDragon.getString("Ctypedes"));
            dragonTigerBean.setJd(jsonDragon.getString("JD"));

            //ogger.info(dragonTigerBean.getShareName()+" "+dragonTigerBean.getShareCode()+" "+dragonTigerBean.getReason());

            dragonTigerBeanList.add(dragonTigerBean);
        }


        return dragonTigerBeanList;
    }
    private void parseDragonDetail(DragonTigerBean dragonTigerBean){
        String date = dragonTigerBean.getCurrentDate();
        String code = dragonTigerBean.getShareCode();

        String detailUrl = config.getLhbDetailUrl();
        detailUrl += date+","+code+".html";

        logger.info(detailUrl);
        HttpUtil httpUtil = HttpUtil.getInstance();

        String htmlContent = httpUtil.getContentByUrl(detailUrl);

//        logger.info(htmlContent);

        Document doc = Jsoup.parse(htmlContent);
        Element tableBuy = doc.getElementById("tab-2");
        Element tbodyBuy = tableBuy.getElementsByTag("tbody").get(0);

        Elements trBuys = tbodyBuy.getElementsByTag("tr");
        for(int i=0;i<trBuys.size();i++){
            Element trBuy = trBuys.get(i);
            Element aBuy = trBuy.getElementsByTag("a").get(1);
            String link = aBuy.attr("href");
            String divisionCode = link.substring(link.lastIndexOf('/') + 1, link.lastIndexOf('.'));


            Elements tds = trBuy.getElementsByTag("td");
            Element td = tds.get(2);

            String val = td.html();

            logger.info(divisionCode+" "+val);
            dragonTigerBean.setBuyByIndex(i,divisionCode,val);
        }



        Element tableSell = doc.getElementById("tab-4");

        Element tbodySell = tableSell.getElementsByTag("tbody").get(0);

        Elements trSells = tbodySell.getElementsByTag("tr");
        for(int i=0;i<trSells.size();i++){
            Element trSell = trSells.get(i);
            Elements aSells = trSell.getElementsByTag("a");
            if(aSells.size()>0){
                Element aSell =aSells.get(1);
                String link = aSell.attr("href");
                String divisionCode = link.substring(link.lastIndexOf('/') + 1, link.lastIndexOf('.'));


                Elements tds = trSell.getElementsByTag("td");
                Element td = tds.get(4);

                String val = td.html();

                logger.info(divisionCode+" "+val);
                dragonTigerBean.setSellByIndex(i,divisionCode,val);
            }

        }


    }
    private boolean isExsitInDragonTigerDb(DragonTigerBean dragonTigerBean){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("shareCode", dragonTigerBean.getShareCode());
        map.put("currentDate", dragonTigerBean.getCurrentDate());
        DragonTigerBean dragonSelect = dragonTigerDao.getDragonTigerByCodeAndDate(map);
        if(dragonSelect!=null){
            return true;
        }
        return false;

    }


    public List<SharesSingleModel> caculateByLHB(String date){
        if(date==null){
            date = dateUtil.getNowDate("yyyy-MM-dd");
        }


        List<SharesSingleModel> singleModels = new ArrayList<SharesSingleModel>();

        //拿到当天的龙虎榜数据
        List<DragonTigerBean> dragonTigerBeans = getDragonTigerListByDate(date);

        //挨个分析每一只
        for(int i=0;i<dragonTigerBeans.size();i++){
            DragonTigerBean dragonTigerBean = dragonTigerBeans.get(i);
//            logger.info(dragonTigerBean.getBuy1Division()+" "+dragonTigerBean.getCurrentDate());
            if(!dragonTigerBean.getShareCode().startsWith("30")
                    && !dragonTigerBean.getShareName().toLowerCase().startsWith("\\*st")
                    && !dragonTigerBean.getShareName().toLowerCase().startsWith("st")) {
                SharesSingleModel singleModel = caculateOneSharesByLHB(dragonTigerBean);
                if (singleModel != null) {
                    singleModels.add(singleModel);
                }
            }
        }


        return singleModels;
    }

    private List<DragonTigerBean> getDragonTigerListByDate(String date){
        Map<String,String> map = new HashMap<String, String>();
        map.put("date", date);
        return dragonTigerDao.getDragonTigerByDate(map);
    }

    private SharesSingleModel caculateOneSharesByLHB(DragonTigerBean dragonTigerBean){
        float buy1 = dragonTigerBean.getBuy1Val();
        float buy2 = dragonTigerBean.getBuy2Val();
        float sell1 = dragonTigerBean.getSell1Val();
        float sell2 = dragonTigerBean.getSell2Val();
        String code = dragonTigerBean.getShareCode();
        SharesSingleModel singleModel = giveMeShareSingleByCode(code);
        if(singleModel==null){
            return null;
        }
        String date = dragonTigerBean.getCurrentDate();
        SharesModel sharesModel = giveMeSharesModelByCodeAndDate(singleModel.getCodeAll(),date);
        if(sharesModel==null){
            return null;
        }


        logger.info(sharesModel.getCode() + " " + sharesModel.getIncreasePer());
        //计算资金流入流出 选取前一天涨幅为正 并且没有涨停的股票
        //logger.info(sharesModel.getCode()+" buy1+buy2:"+(buy1+buy2));
        //logger.info(sharesModel.getCode() + " sell1+sell2:" + (sell1 + sell2));
        if((buy1+buy2)/(sell1+sell2)>2){
            if(sharesModel.getChangePer()<20&&sharesModel.getChangePer()>10) {

                if (sharesModel.getIncreasePer() > 9.5) {
                    //涨停

                    SharesModel sharesModelPre = giveMeSharesModelPreByCodeAndDate(singleModel.getCodeAll(), date);
                    logger.info(sharesModelPre.getDate());
                    logger.info(sharesModel.getCode() + "今日涨停，昨日涨幅：" + sharesModelPre.getIncreasePer());
                    if (sharesModelPre.getIncreasePer() < 9.5) {
                        if (checkJG(dragonTigerBean) >= 0) {
                            logger.info("选出：" + singleModel.getName() + " " + singleModel.getCodeAll());
                            return singleModel;
                        } else {
                            return null;
                        }
                    }
                } else {
                    logger.info("今日未涨停");
                    logger.info("选出：" + singleModel.getName() + " " + singleModel.getCodeAll());
                    return singleModel;
                }
            }else if(sharesModel.getChangePer()<10){
                if (checkJG(dragonTigerBean) > 0) {
                    logger.info("选出：" + singleModel.getName() + " " + singleModel.getCodeAll());
                    return singleModel;
                }
            }
        }

        if(checkJG(dragonTigerBean)>0&&dragonTigerBean.getBuy1Division()==-1) {
            logger.info("选出：" + singleModel.getName() + " " + singleModel.getCodeAll());
            return singleModel;
        }



        return null;
    }
    private int checkJG(DragonTigerBean dragonTigerBean){
        int buyJG = 0;
        int sellJG = 0;


        if(dragonTigerBean.getBuy1Division()==-1){
            buyJG++;
        }
        if(dragonTigerBean.getBuy2Division()==-1){
            buyJG++;
        }
        if(dragonTigerBean.getBuy3Division()==-1){
            buyJG++;
        }
        if(dragonTigerBean.getBuy4Division()==-1){
            buyJG++;
        }
        if(dragonTigerBean.getBuy5Division()==-1){
            buyJG++;
        }
        if(dragonTigerBean.getSell1Division()==-1){
            sellJG++;
        }
        if(dragonTigerBean.getSell2Division()==-1){
            sellJG++;
        }
        if(dragonTigerBean.getSell3Division()==-1){
            sellJG++;
        }
        if(dragonTigerBean.getSell4Division()==-1){
            sellJG++;
        }
        if(dragonTigerBean.getSell5Division()==-1){
            sellJG++;
        }

        return buyJG-sellJG;

    }



    private SharesSingleModel giveMeShareSingleByCode(String code){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code", code);
        List<SharesSingleModel> singleModelHitList = sharesDao.getShares(map);
        if(singleModelHitList.size()>0){
            SharesSingleModel  singleModel = singleModelHitList.get(0);
            return singleModel;
        }
        return null;
    }

    private SharesModel giveMeSharesModelPreByCodeAndDate(String code,String date) {
        String datePre = dateUtil.getAddDate(date, "yyyy-MM-dd", -24 * 3600000);
        String dateStart = dateUtil.getAddDate(date, "yyyy-MM-dd", -24L * 10 * 3600000);
        //查出当天的数据
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("date",datePre);
        map.put("beginDate", dateStart);
        List<SharesModel> sharesModels = sharesHistoryDao.selectModelByCode(map);
        if(sharesModels.size()>0){
            return sharesModels.get(0);
        }
        return null;
    }

    private SharesModel giveMeSharesModelByCodeAndDate(String code,String date){
        //查出当天的数据
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code",code);
        map.put("date",date);
        map.put("beginDate", date);
        List<SharesModel> sharesModels = sharesHistoryDao.selectModelByCode(map);
        if(sharesModels.size()>0){
            return sharesModels.get(0);
        }
        return null;
    }

    public List<LHBCacularResult> validateCaculateByLHB(String date){
//        List<SharesSingleModel> singleModels = caculateByLHB(date);
        List<SharesSingleModel> singleModels = caculateByLHBFromListDragon(date);
        List<LHBCacularResult> lhbCacularResults = validateCaculate(singleModels, date);
        return lhbCacularResults;
    }

    /**
     *
     * @param sharesSingleModels    验证列表
     * @param date  当前龙虎榜日期
     */
    public List<LHBCacularResult> validateCaculate(List<SharesSingleModel> sharesSingleModels,String date){
        List<LHBCacularResult> lhbCacularResults = new ArrayList<LHBCacularResult>();

        for(int i=0;i<sharesSingleModels.size();i++){
            SharesSingleModel singleModel = sharesSingleModels.get(i);
            lhbCacularResults.add(validateCaculateOne(singleModel,date));
        }

        return lhbCacularResults;
    }

    public LHBCacularResult validateCaculateOne(SharesSingleModel sharesSingleModel,String date){
        LHBCacularResult lhbCacularResult = new LHBCacularResult();
        lhbCacularResult.setShareCode(sharesSingleModel.getCodeAll());
        lhbCacularResult.setShareName(sharesSingleModel.getName());

        String code = sharesSingleModel.getCodeAll();
        //获取 当前代码 date日期之后的数据
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code",code);
        map.put("date",date);
        List<SharesModel> shareModels = sharesHistoryDao.selectSharesByCodeAfterDate(map);
        if(shareModels.size()>2){
            SharesModel day0 = shareModels.get(0);
            SharesModel day1 = shareModels.get(1);
            float cb = day1.getOpen();
            float closeDay0 = day0.getClose();
            float changePer0 = day0.getChangePer();
            if((cb-closeDay0)/closeDay0>0.03){
                cb = day1.getLow();
                if((cb-closeDay0)/closeDay0>0.03) {
                    lhbCacularResult.setIsSuccess(false);
                    lhbCacularResult.setDesc("放弃操作");
                    return  lhbCacularResult;
                }else{//如果最低值是小于0.03的可以选择合适机会买入
                    cb = closeDay0*1.03f;
                    lhbCacularResult.setBuy(cb);
                    lhbCacularResult.setBuyDate(day1.getDate());
                }
            }else{
                lhbCacularResult.setBuy(cb);
                lhbCacularResult.setBuyDate(day1.getDate());
            }

            float changePer1 = day1.getChangePer();
            if(changePer1-changePer0>0){
                //换手率升高有出货嫌疑，所以第二天不管是否盈利 立马卖出
                SharesModel day2 = shareModels.get(2);
                float open = day2.getOpen();
                if(open!=0){
                    float increase = (open - cb) / cb;
                    lhbCacularResult.setIncrease(increase);
                    lhbCacularResult.setSell(open);
                    lhbCacularResult.setSellDate(day2.getDate());
                    if(increase>0.05){
                        lhbCacularResult.setIsSuccess(true);
                        lhbCacularResult.setDesc("止盈出局 开盘涨过5%");
                    }else if(increase<-0.05){
                        lhbCacularResult.setIsSuccess(false);
                        lhbCacularResult.setDesc("止损出局 开盘跌过5%");
                    }else if(increase>0){
                        lhbCacularResult.setIsSuccess(true);
                        lhbCacularResult.setDesc("止盈出局 微利"+increase);
                    }else{
                        lhbCacularResult.setIsSuccess(false);
                        lhbCacularResult.setDesc("止损出局 亏损"+increase);
                    }
                }else{
                    lhbCacularResult.setIsSuccess(false);
                    lhbCacularResult.setDesc("没有结果");
                    lhbCacularResult.setSell(cb);
                    lhbCacularResult.setIncrease(0);
                    lhbCacularResult.setSellDate(dateUtil.getAddDate(lhbCacularResult.getBuyDate(),"yyyy-MM-dd",1*24*3600000));
                }
            }else{
                for(int i=2;i<shareModels.size()&&i<5;i++){
                    SharesModel dayi = shareModels.get(i);
                    float open = dayi.getOpen();
                    if(open!=0){
                        float increase = (open - cb) / cb;
                        if (increase < -0.05) {
                            //割肉
                            lhbCacularResult.setIsSuccess(false);
                            lhbCacularResult.setIncrease(increase);
                            lhbCacularResult.setSell(open);
                            lhbCacularResult.setSellDate(dayi.getDate());
                            lhbCacularResult.setDesc("止损出局 开盘跌过5%");
                            break;
                        } else if (increase > 0.05) {
                            //盈利出货
                            lhbCacularResult.setIsSuccess(true);
                            lhbCacularResult.setSell(open);
                            lhbCacularResult.setSellDate(dayi.getDate());
                            lhbCacularResult.setIncrease(increase);
                            lhbCacularResult.setDesc("止盈出局 开盘涨过5%");
                            break;
                        } else {
                            //比较最高 最低值
                            float low = dayi.getLow();
                            float lowIncrease = (low - cb) / cb;
                            float high = dayi.getHigh();
                            float highIncrease = (high - cb) / cb;
                            if (lowIncrease < -0.05) {
                                //割肉
                                lhbCacularResult.setIsSuccess(false);
                                lhbCacularResult.setIncrease(-0.05f);
                                lhbCacularResult.setSell(cb * 0.95f);
                                lhbCacularResult.setSellDate(dayi.getDate());
                                lhbCacularResult.setDesc("止损出局 盘中跌过5%");
                                break;
                            } else if (highIncrease > 0.05) {
                                //盈利出货
                                lhbCacularResult.setIsSuccess(true);
                                lhbCacularResult.setSell(cb * 1.05f);
                                lhbCacularResult.setSellDate(dayi.getDate());
                                lhbCacularResult.setIncrease(0.05f);
                                lhbCacularResult.setDesc("止盈出局 盘中涨过5%");
                                break;
                            }

                        }
                    }
                    if(i==shareModels.size()-1||i==4){
                        lhbCacularResult.setIsSuccess(false);
                        lhbCacularResult.setDesc("没有结果");
                        lhbCacularResult.setSell(cb*0.95f);
                        lhbCacularResult.setIncrease(-0.05f);
                        lhbCacularResult.setSellDate(dateUtil.getAddDate(lhbCacularResult.getBuyDate(),"yyyy-MM-dd",3*24*3600000));

                    }
                }
            }


        }else{
            lhbCacularResult.setIsSuccess(false);
            lhbCacularResult.setDesc("没有结果");
        }

        return lhbCacularResult;
    }

    public LHBCacularResult validateCaculateOneDay(SharesSingleModel sharesSingleModel,String date){
        LHBCacularResult lhbCacularResult = new LHBCacularResult();
        lhbCacularResult.setShareCode(sharesSingleModel.getCodeAll());
        lhbCacularResult.setShareName(sharesSingleModel.getName());

        String code = sharesSingleModel.getCodeAll();
        //获取 当前代码 date日期之后的数据
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code",code);
        map.put("date",date);
        List<SharesModel> shareModels = sharesHistoryDao.selectSharesByCodeAfterDate(map);
        if(shareModels.size()>1){
            SharesModel day0 = shareModels.get(0);
            SharesModel day1 = shareModels.get(1);
            float cb = day1.getOpen();
            float closeDay0 = day0.getClose();
            if((cb-closeDay0)/closeDay0>0.03){
                lhbCacularResult.setIsSuccess(false);
                lhbCacularResult.setDesc("放弃操作");
            }else{
                lhbCacularResult.setBuy(cb);
                lhbCacularResult.setBuyDate(day1.getDate());
                lhbCacularResult.setSell(day1.getClose());
                float increase = (day1.getClose() - cb) / cb;
                lhbCacularResult.setIncrease(increase);
                if(increase>0.03){
                    lhbCacularResult.setDesc("值得操作");
                    lhbCacularResult.setIsSuccess(true);
                }else{
                    lhbCacularResult.setDesc("不值得操作");
                    lhbCacularResult.setIsSuccess(false);
                }
            }

        }else{
            lhbCacularResult.setIsSuccess(false);
            lhbCacularResult.setDesc("没有结果");
        }

        return lhbCacularResult;
    }

    private List<SharesModel> giveMeSharesModelsByCodeAfterDate(String code,String date){
        return null;
    }

    public List<DivisionBean> listDivisions(String date,String dayNumStr){
        if(date==null){
            date = dateUtil.getNowDate("yyyy-MM-dd");
        }
        int dayNum = 30;
        if(dayNumStr!=null){
            dayNum = Integer.parseInt(dayNumStr);
        }
        List<LHBCacularResult> lhbCacularResults = new ArrayList<LHBCacularResult>();
        for(int i=0;i<dayNum;i++){
            date = dateUtil.getAddDate(date,"yyyy-MM-dd",-24L*3600000);
            List<DragonTigerBean> dragonTigerBeans = getDragonTigerListByDate(date);
            for(int j=0;j<dragonTigerBeans.size();j++){
                DragonTigerBean dragonTigerBean = dragonTigerBeans.get(j);
                SharesSingleModel singleModel = giveMeSharesSingleByLHB(dragonTigerBean);
                if(singleModel!=null){
                    LHBCacularResult lhbCacularResult = validateCaculateOne(singleModel,dragonTigerBean.getCurrentDate());
                    lhbCacularResults.add(lhbCacularResult);
                    lhbCacularResult.setDragonTigerBean(dragonTigerBean);
                }
                logger.info(dragonTigerBean.getShareCode()+" "+dragonTigerBean.getCurrentDate()+" 进入龙虎榜");
            }
        }

        Map<Integer,Integer> divisionCodeNumMap = new HashMap<Integer, Integer>();
        for(int i=0;i<lhbCacularResults.size();i++){
            LHBCacularResult lhbCacularResult = lhbCacularResults.get(i);

            if(lhbCacularResult.isSuccess()){
                addDivisionMapByLHBResult(lhbCacularResult,divisionCodeNumMap);
            }

        }

        return sortDivisionByMap(divisionCodeNumMap);
    }
    private List<DivisionBean> sortDivisionByMap(Map<Integer,Integer> divisionCodeNumMap){
        Set<Integer> keySet = divisionCodeNumMap.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        List<DivisionBean> divisionBeans = new ArrayList<DivisionBean>();
        while (iterator.hasNext()){
            int divisionCode = iterator.next();
            DivisionBean divisionBean = giveMeDivisionBeanByCode(divisionCode);
            if(divisionBean!=null){
                divisionBean.setSuccessNum(divisionCodeNumMap.get(divisionCode));
                divisionBeans.add(divisionBean);
            }
        }
        Collections.sort(divisionBeans, new Comparator<DivisionBean>() {
            public int compare(DivisionBean o1, DivisionBean o2) {
                return o2.getSuccessNum() - o1.getSuccessNum();
            }
        });
        return divisionBeans;
    }

    private DivisionBean giveMeDivisionBeanByCode(int divisionCode){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code", divisionCode);
        DivisionBean divisionSelect = divisionDao.getDivisionByCode(map);
        return divisionSelect;
    }

    //将营业部放入计数map
    private void addDivisionMapByLHBResult(LHBCacularResult lhbCacularResult,Map<Integer,Integer> divisionCodeNumMap){
        DragonTigerBean dragonTigerBean = lhbCacularResult.getDragonTigerBean();
        int divisionCode = dragonTigerBean.getBuy1Division();
        if(!divisionCodeNumMap.containsKey(divisionCode)){
            divisionCodeNumMap.put(divisionCode,1);
        }else{
            divisionCodeNumMap.put(divisionCode, divisionCodeNumMap.get(divisionCode) + 1);
        }

    }

    private SharesSingleModel giveMeSharesSingleByLHB(DragonTigerBean dragonTigerBean){
        String code = dragonTigerBean.getShareCode();
        SharesSingleModel singleModel = giveMeShareSingleByCode(code);
        return  singleModel;
    }

    public List<LHBCacularResult> caculateSuccessPer(String date,String dayNumStr) {
        if (date == null) {
            date = dateUtil.getNowDate("yyyy-MM-dd");
        }
        int dayNum = 30;
        if (dayNumStr != null) {
            dayNum = Integer.parseInt(dayNumStr);
        }
        List<LHBCacularResult> lhbCacularResults = new ArrayList<LHBCacularResult>();
        for (int i = 0; i < dayNum; i++) {
            date = dateUtil.getAddDate(date, "yyyy-MM-dd", -24L * 3600000);
            lhbCacularResults.addAll(validateCaculateByLHB(date));
        }
        return lhbCacularResults;

    }

    public float cuculateSuccessPerByLHBResultList(List<LHBCacularResult> lhbCacularResults){
        float successNum = 0;
        float failNum = 0;
        for(int i=0;i<lhbCacularResults.size();i++){
            LHBCacularResult lhbCacularResult = lhbCacularResults.get(i);
            if(lhbCacularResult.isSuccess()){
                successNum ++;
            }else{
                if(!lhbCacularResult.getDesc().equals("放弃操作")
                        &&!lhbCacularResult.getDesc().equals("没有结果")){
                    failNum ++;
                }
            }
        }
        if(failNum==0&&successNum==0){
            return -1;
        }
        logger.info("复盘结果 成功预测次数 ：" + successNum + " 失败次数：" + failNum);
        return successNum/(successNum+failNum);
    }

    public List<LHBCacularResult> listDragonResultByDate(String date){
        if(date==null){
            date = dateUtil.getNowDate("yyyy-MM-dd");
        }
        List<DragonTigerBean> dragonTigerBeans = getDragonTigerListByDate(date);
        List<LHBCacularResult> lhbCacularResults = new ArrayList<LHBCacularResult>();
        for(int i=0;i<dragonTigerBeans.size();i++){
            DragonTigerBean dragonTigerBean = dragonTigerBeans.get(i);
            String shareCode = dragonTigerBean.getShareCode();
            if(shareCode.startsWith("300")){
                dragonTigerBeans.remove(i);
                i--;
            }else {
                SharesSingleModel singleModel = giveMeShareSingleByCode(dragonTigerBean.getShareCode());
                if(singleModel!=null){
                    LHBCacularResult lhbCacularResult = validateCaculateOne(singleModel, date);
                    lhbCacularResults.add(lhbCacularResult);
                    lhbCacularResult.setDragonTigerBean(dragonTigerBean);
                }else{
                    logger.info(dragonTigerBean.getShareCode()+"没有找到这只股票");
                }
            }
        }
        return lhbCacularResults;
    }

    public List<LHBCacularResult> listDragonResultOneByOne(String date){
        if(date==null){
            date = dateUtil.getNowDate("yyyy-MM-dd");
        }
        List<DragonTigerBean> dragonTigerBeans = getDragonTigerListByDate(date);
        List<LHBCacularResult> lhbCacularResults = new ArrayList<LHBCacularResult>();
        for(int i=0;i<dragonTigerBeans.size();i++){
            DragonTigerBean dragonTigerBean = dragonTigerBeans.get(i);
            String shareCode = dragonTigerBean.getShareCode();
            if(shareCode.startsWith("300")){
                dragonTigerBeans.remove(i);
                i--;
            }else {
                SharesSingleModel singleModel = giveMeShareSingleByCode(dragonTigerBean.getShareCode());
                if(singleModel!=null){
                    LHBCacularResult lhbCacularResult = validateCaculateOneDay(singleModel,date);
                    lhbCacularResults.add(lhbCacularResult);
                    lhbCacularResult.setDragonTigerBean(dragonTigerBean);
                }else{
                    logger.info(dragonTigerBean.getShareCode()+"没有找到这只股票");
                }
            }
        }
        return lhbCacularResults;
    }

    public List<SimulateBean> simulateOp(String dateBegin,String dateEnd,float allMoney){
        float allMoneyInit = allMoney;

        List<SimulateBean> simulateBeans = new ArrayList<SimulateBean>();
        float currentOpMoney = 10000;
        float elseMoney = allMoney - currentOpMoney;
        String currentDate = dateBegin;

        while (true){
            SimulateBean simulateBean = simulateOpByDate(currentOpMoney,currentDate);
            if(simulateBean==null){
                logger.info("模拟失败，当前日期："+currentDate);
                currentDate = dateUtil.getAddDate(currentDate,"yyyy-MM-dd",24*3600000);

            }else{

                logger.info("模拟成功，当前日期："+currentDate);
                currentDate = dateUtil.getAddDate(simulateBean.getEndDate(),"yyyy-MM-dd",-24*3600000);

                float simuAllMoney = simulateBean.getAllMoney();
                allMoney = elseMoney + simuAllMoney;

                simulateBean.setAllMoney(allMoney);


                LHBCacularResult lhbCacularResult = simulateBean.getLhbCacularResult();
                if(lhbCacularResult.isSuccess()){
                    /*if(allMoney-allMoneyInit>0) {
                        currentOpMoney = 10000;
                    }else{
                        currentOpMoney = (allMoneyInit-allMoney)*100/5;
                        if(currentOpMoney>allMoney){
                            currentOpMoney = allMoney;
                        }
                    }*/
                    if(lhbCacularResult.getDesc().indexOf("微利")!=-1){

                    }else{
                        currentOpMoney = 10000;
                    }

                }else{
                    currentOpMoney = 10000;
//                    currentOpMoney = currentOpMoney*2;
//                    if(currentOpMoney>allMoney){
//                        logger.info("注意风险，要爆仓了！");
//                        currentOpMoney = currentOpMoney/2;//如果爆仓，则使用当前资金的一半左右操作资金
//                    }
                }
                elseMoney = allMoney - currentOpMoney;

                simulateBean.setIncomePer((allMoney-allMoneyInit)/allMoneyInit);

                simulateBeans.add(simulateBean);
            }
            if(dateUtil.compareDate(currentDate,dateEnd)>=0){
                break;
            }
        }

        return simulateBeans;
    }

    private SimulateBean simulateOpByDate(float inMoney,String date){
        //在 date这天，有inMoney这么多钱
        //拿到当前 dateBegin 的 选中的股票
//        List<SharesSingleModel> singleModels = caculateByLHB(date);
        List<SharesSingleModel> singleModels = caculateByLHBFromListDragon(date);
        LHBCacularResult lhbCacularResult = simulateOpBySingleModels(singleModels, date);
        if(lhbCacularResult!=null){
            logger.info(lhbCacularResult.getDesc());
            boolean isSuccess = lhbCacularResult.isSuccess();
            float buy = lhbCacularResult.getBuy();
            float sell = lhbCacularResult.getSell();
            if((int)(buy*100)==0){
                return null;
            }else{
                int num = (int)inMoney/(int)(buy*100);

                float allBuy = buy*num*100;
                float allSell = sell*num*100;
                float allMoney = inMoney-allBuy+allSell;


                SimulateBean simulateBean = new SimulateBean();

                simulateBean.setCurrentDate(lhbCacularResult.getBuyDate());
                simulateBean.setEndDate(lhbCacularResult.getSellDate());
                simulateBean.setLhbCacularResult(lhbCacularResult);
                simulateBean.setMoney(allBuy);
                simulateBean.setEndMoney(allSell);
                simulateBean.setAllMoney(allMoney);

                return  simulateBean;
            }



        }
        return  null;

    }



    private LHBCacularResult simulateOpBySingleModels(List<SharesSingleModel> singleModels,String date){
        if(singleModels.size()>0) {
            Random r = new Random();
            int indexHit = r.nextInt(singleModels.size());
            SharesSingleModel singleModel = singleModels.get(indexHit);

            LHBCacularResult lhbCacularResult = validateCaculateOne(singleModel, date);
            boolean isSuccess = lhbCacularResult.isSuccess();
            if(!isSuccess){
                String desc = lhbCacularResult.getDesc();
                if(desc.equals("放弃操作")){
                    singleModels.remove(indexHit);
                    return simulateOpBySingleModels(singleModels,date);
                }
            }
            return lhbCacularResult;
        }
        return null;
    }

    public List<SharesSingleModel> caculateByLHBFromListDragon(String date){
        List<DragonTigerBean> dragonTigerBeans = listSelectDragon(date);
        List<SharesSingleModel> singleModels = new ArrayList<SharesSingleModel>();
        for(int i=0;i<dragonTigerBeans.size();i++){
            DragonTigerBean dragonTigerBean = dragonTigerBeans.get(i);
            SharesSingleModel singleModel = giveMeShareSingleByCode(dragonTigerBean.getShareCode());
            singleModels.add(singleModel);
        }
        return singleModels;
    }

    //主观选择法
    public List<DragonTigerBean> listSelectDragon(String date){
        if(date==null){
            date = dateUtil.getNowDate("yyyy-MM-dd");
        }

        //拿到当天的龙虎榜数据
        List<DragonTigerBean> dragonTigerBeans = getDragonTigerListByDate(date);
        List<DragonTigerBean> selectDragons = new ArrayList<DragonTigerBean>();

        //挨个分析每一只
        for(int i=0;i<dragonTigerBeans.size();i++){
            DragonTigerBean dragonTigerBean = dragonTigerBeans.get(i);

            String code = dragonTigerBean.getShareCode();
            String name = dragonTigerBean.getShareName();
            //将创业板 st 踢除
            if(code.startsWith("30")
                || name.toLowerCase().startsWith("\\*st")
                || name.toLowerCase().startsWith("st")) {
                continue;
            }

            if(!fillterByBS(dragonTigerBean)){//买 大于 卖
                continue;
            }

            if(!fillterByJG(dragonTigerBean)){//机构权重 买 大于 卖
                continue;
            }
            if(!fillterByIncreseAndChangePer(dragonTigerBean)){//上升通道
                continue;
            }
            logger.info(dragonTigerBean.getShareCode()+" "+dragonTigerBean.getShareName()+" "+dragonTigerBean.getCurrentDate()+" 被选出" );
            selectDragons.add(dragonTigerBean);

        }

        return selectDragons;
    }

    private boolean fillterByBS(DragonTigerBean dragonTigerBean){
        float allBuy = dragonTigerBean.getAllBuy();
        float allSell = dragonTigerBean.getAllSell();
        float mainBuy = dragonTigerBean.getMainBuy();
        float mainSell = dragonTigerBean.getMainSell();
        if(allBuy>allSell*2 && mainBuy>mainSell*3){
            //买入资金有规模效应
            return true;
        }
        return false;
    }
    private boolean fillterByJG(DragonTigerBean dragonTigerBean){
        int buyJGCode = dragonTigerBean.getBuyJGCode();
        int sellJGCode = dragonTigerBean.getSellJGCode();
        if(buyJGCode>=sellJGCode){
            return true;
        }
        return false;
    }
    private boolean fillterByIncreseAndChangePer(DragonTigerBean dragonTigerBean){
        //查出 当天 涨幅
        String code = dragonTigerBean.getShareCode();
        String date = dragonTigerBean.getCurrentDate();
        SharesSingleModel singleModel = giveMeShareSingleByCode(code);
        if(singleModel==null){
            return false;
        }
        SharesModel sharesModel = giveMeSharesModelByCodeAndDate(singleModel.getCodeAll(), date);
        if(sharesModel==null){
            return false;
        }
        float increasePer = sharesModel.getIncreasePer();
        float changePer = sharesModel.getChangePer();
        float close = sharesModel.getClose();
        if(increasePer>0 && changePer>1 && close>7){
            return true;
        }
        return false;
    }

}
