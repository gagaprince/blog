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

    public void spiderLHBHistory(String date){
        if(date==null){
            date = dateUtil.getNowDate("yyyy-MM-dd");
        }
        String nowDate = date;
        for(int i=1;i<150;i++){
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
            SharesSingleModel singleModel = caculateOneSharesByLHB(dragonTigerBean);
            if(singleModel!=null){
                singleModels.add(singleModel);
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
//        logger.info(sharesModel.getCode() + " " + sharesModel.getIncreasePer());

        logger.info(sharesModel.getCode()+" buy1+buy2:"+(buy1+buy2));
        logger.info(sharesModel.getCode() + " sell1+sell2:" + (sell1 + sell2));
        if((buy1+buy2)/(sell1+sell2)>2){

            if(sharesModel.getIncreasePer()>9.5){
                //涨停

                SharesModel sharesModelPre = giveMeSharesModelPreByCodeAndDate(singleModel.getCodeAll(), date);
                logger.info(sharesModelPre.getDate());
                logger.info(sharesModel.getCode()+"今日涨停，昨日涨幅："+sharesModelPre.getIncreasePer());
                if(sharesModelPre.getIncreasePer()<9.5 && sharesModelPre.getIncreasePer()>0){
                    logger.info("选出："+singleModel.getName()+" "+singleModel.getCodeAll());
                    return singleModel;
                }
            }
        }


        return null;
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

    private SharesModel giveMeSharesModelPreByCodeAndDate(String code,String date){
        String datePre = dateUtil.getAddDate(date,"yyyy-MM-dd",-24*3600000);
        String dateStart = dateUtil.getAddDate(date,"yyyy-MM-dd",-24L*10*3600000);
        //查出当天的数据
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code",code);
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

    public void validateCaculateByLHB(String date){
        List<SharesSingleModel> singleModels = caculateByLHB(date);
        validateCaculate(singleModels,date);

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

        String code = sharesSingleModel.getCodeAll();
        //获取 当前代码 date日期之后的数据



        return lhbCacularResult;
    }

    private List<SharesModel> giveMeSharesModelsByCodeAfterDate(String code,String date){
        return null;
    }
}
