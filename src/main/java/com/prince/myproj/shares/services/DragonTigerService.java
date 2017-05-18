package com.prince.myproj.shares.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prince.myproj.shares.dao.DivisionDao;
import com.prince.myproj.shares.dao.DragonTigerDao;
import com.prince.myproj.shares.models.DivisionBean;
import com.prince.myproj.shares.models.DragonTigerBean;
import com.prince.myproj.shares.models.ShareConfig;
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

    public void spiderLHBHistory(){

        String nowDate = dateUtil.getNowDate("yyyy-MM-dd");
        for(int i=1;i<31;i++){
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
        List<DragonTigerBean> dragonTigerBeans = getDragonListFromListUrl(listUrl,date);

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
        DragonTigerBean dragonSelect = dragonTigerDao.getDivisionByCodeAndDate(map);
        if(dragonSelect!=null){
            return true;
        }
        return false;

    }
}
