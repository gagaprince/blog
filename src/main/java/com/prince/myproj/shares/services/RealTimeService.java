package com.prince.myproj.shares.services;

import com.prince.myproj.shares.models.CheckShareModel;
import com.prince.myproj.shares.models.ShareConfig;
import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gagaprince on 2017/6/20.
 */
@Service
public class RealTimeService {

    public static final Logger logger = Logger.getLogger(RealTimeService.class);

    @Autowired
    private ShareConfig config;

    @Autowired
    private SharesHistoryDataService sharesHistoryDataService;


    public List<SharesSingleModel> filterSomeShares(){
        List<SharesSingleModel> sharesSingleModels = sharesHistoryDataService.getSharesModelsWithOutSC(0,3000);

        return  sharesSingleModels;

    }


    public Map<String,List<Map<String,Object>>> getRealTimeSharesData(List<SharesSingleModel> sharesSingleModels){
        if(sharesSingleModels==null){
            sharesSingleModels = filterSomeShares();
        }

        int length = 100;
        int begin = 0;
        List<SharesSingleModel> sharesSingleModelsSelect = new ArrayList<SharesSingleModel>();
        String content = "";
        while(true) {

            for (int i = begin; i < begin + length && i < sharesSingleModels.size(); i++) {
                sharesSingleModelsSelect.add(sharesSingleModels.get(i));

            }
            content += getContentSplit(sharesSingleModelsSelect);
            sharesSingleModelsSelect.clear();
            begin += length;
            if(begin>=sharesSingleModels.size()){
                break;
            }
        }
        return parseContent(content);
    }

    private String getContentSplit(List<SharesSingleModel> sharesSingleModels){
        String url = parseUrlByList(sharesSingleModels);
        if(url!=null){
//            logger.info(url);
            HttpUtil httpUtil = HttpUtil.getInstance();
            String todayContent = httpUtil.getContentByUrl(url);
            return todayContent;
        }
        return "";
    }

    private Map<String,List<Map<String,Object>>> parseContent(String content){
        List<Map<String,Object>> currentOverList = new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> hasOverList = new ArrayList<Map<String, Object>>();

//        logger.info(content);
        String[] codeContents = content.split(";\n");
        int length = codeContents.length;
        logger.info("length:" + length);
        for(int i=0;i<length;i++){
            String codeContent = codeContents[i];
            String[] fields = codeContent.split(",");
            if(fields.length>=32){
                String code = fields[0].substring(11,19);
                String name = fields[0].substring(fields[0].indexOf("\"")+1);
                float open = Float.parseFloat(fields[1]);
                float close = Float.parseFloat(fields[2]);
                float nowPrice = Float.parseFloat(fields[3]);
                float high = Float.parseFloat(fields[4]);

                float currentIncreasePer = (nowPrice-close)/close;

                float currentHighIncreasePer = (high-close)/close;

                if(currentHighIncreasePer>0.0997){

                    Map<String,Object> sharesObj = new HashMap<String, Object>();
                    sharesObj.put("code",code);
                    sharesObj.put("name",name);
                    sharesObj.put("open",open);
                    sharesObj.put("high",high);
                    sharesObj.put("yestodayClose",close);
                    sharesObj.put("nowPrice",nowPrice);
                    //之前涨停过
                    if(currentIncreasePer>0.0997){
                        //当前涨停
                        currentOverList.add(sharesObj);
                    }else {
                        hasOverList.add(sharesObj);
                    }
                }

            }

        }

        Map<String,List<Map<String,Object>>> realTimeSharesMap = new HashMap<String, List<Map<String, Object>>>();
        realTimeSharesMap.put("currentOver",currentOverList);
        realTimeSharesMap.put("hasOver",hasOverList);
        return realTimeSharesMap;

    }

    private String parseUrlByList(List<SharesSingleModel> sharesSingleModels){
        if(sharesSingleModels!=null&&sharesSingleModels.size()>0){
            int size = sharesSingleModels.size();
            StringBuffer sb = new StringBuffer("");
            for(int i=0;i<size;i++){
                SharesSingleModel model = sharesSingleModels.get(i);
                sb.append(model.getCodeAll()).append(",");
            }
            String url = config.getRealTimeUrl()+sb.toString();
            return url;
        }
        return null;
    }

}
