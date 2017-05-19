package com.prince.myproj.shares.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prince.myproj.shares.dao.SharesDao;
import com.prince.myproj.shares.models.ShareConfig;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zidong.wang on 2016/5/17.
 */
@Service
public class ShareCodeGetService {
    public static final Logger logger = Logger.getLogger(ShareCodeGetService.class);


    //抓取股票代码列表
    @Autowired
    private ShareConfig config;
    @Autowired
    private SharesDao sharesDao;


    public void getAllCodeFromSina(){
        String codeUrl = config.getShareCodeUrl();
        //拼接参数网络请求 此处查8页
        for(int i=1;i<=8;i++){
            String params = "[[\"hq\",\"hs_a\",\"\",0,"+i+",400]]";
            String realCodeUrl = "";
            try {
                realCodeUrl = codeUrl+ URLEncoder.encode(params, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            logger.info(realCodeUrl);
            HttpUtil httpUtil = HttpUtil.getInstance();
            String codeContent = httpUtil.getContentByUrl(realCodeUrl);
            logger.info(codeContent);

            parseCode(codeContent);

        }

        //保存数据库

    }

    private void parseCode(String content){
        JSONArray codesJsonArray = JSON.parseArray(content);
        JSONObject codesJson = codesJsonArray.getJSONObject(0);
        JSONArray codesArray = codesJson.getJSONArray("items");
        int size = codesArray.size();
        for(int i=0;i<size;i++){
            JSONArray codeJson = codesArray.getJSONArray(i);
            String codeAll = codeJson.getString(0);
            String code = codeJson.getString(1);
            String name = codeJson.getString(2);
            logger.info("codeAll:" + codeAll + " code:" + code + " name:" + name);
            SharesSingleModel sharesSingleModel = new SharesSingleModel();
            sharesSingleModel.setCode(code);
            sharesSingleModel.setCodeAll(codeAll);
            sharesSingleModel.setName(name);

            if(!isExistSharesInDb(codeAll)){
                sharesDao.save(sharesSingleModel);
            }

        }
    }

    private boolean isExistSharesInDb(String codeAll){
        HashMap<String,Object> paramMap = new HashMap<String, Object>();

        List<String> codeList = new ArrayList<String>();
        codeList.add(codeAll);
        paramMap.put("codes", codeList);
        List<SharesSingleModel> sharesSingleModels = sharesDao.getSharesIncodes(paramMap);
        if(sharesSingleModels.size()>0){
            return true;
        }

        return false;
    }

}
