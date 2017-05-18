package com.prince.myproj.shares.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prince.myproj.shares.dao.DivisionDao;
import com.prince.myproj.shares.models.DivisionBean;
import com.prince.myproj.shares.models.ShareConfig;
import com.prince.util.httputil.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        map.put("code",divisionBean.getCode());
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
            companyMap.put("name",name);
            companyMap.put("code",companyCode);

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

}
