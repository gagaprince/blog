package com.prince.myproj.shares.services;

import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.myproj.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gagaprince on 2017/5/31.
 */
@Service
public class SharesKService {
    public static final Logger logger = Logger.getLogger(SharesKService.class);

    private DateUtil dateUtil = new DateUtil();

    @Autowired
    public SharesHistoryDataService sharesHistoryDataService;

    public List<SharesSingleModel> findSharesByK(String date){
        int start = 0;
        int end = 3000;
        List<SharesSingleModel> sharesSingleModels = sharesHistoryDataService.getSharesModels(start,end);

        List<SharesSingleModel> selectShares=new ArrayList<SharesSingleModel>();

        String preStartDate = dateUtil.getAddDate(date,"yyyy-MM-dd",-24*5*3600000);
        String preEndDate = dateUtil.getAddDate(date, "yyyy-MM-dd", -24 * 3600000);
        logger.info("昨日是："+preEndDate);

        for(int i=0;i<sharesSingleModels.size();i++){
            SharesSingleModel sharesSingleModel = sharesSingleModels.get(i);
            if(judgeSharesSingleByK(sharesSingleModel,date)){
                List<SharesModel> preSharesModels = sharesHistoryDataService.getModelsByStartEndDate(sharesSingleModel.getCodeAll(), preStartDate,preEndDate);
                SharesModel preSharesModel = null;
                if(preSharesModels.size()>0){
                    preSharesModel = preSharesModels.get(preSharesModels.size()-1);
                }
                if(preSharesModel!=null) {
                    logger.info("前一日涨幅：" + preSharesModel.getIncreasePer());
                    if (preSharesModel.getIncreasePer() > 0) {
                        selectShares.add(sharesSingleModel);
                    }
                }
            }
        }
        return selectShares;
    }

    private boolean judgeSharesSingleByK(SharesSingleModel sharesSingleModel,String date){

        String code = sharesSingleModel.getCode();
        if(code.startsWith("30")){
            return false;
        }
        if(code.equals("000001")||code.equals("399001")||code.equals("399006")){
            return false;
        }
        String codeAll = sharesSingleModel.getCodeAll();

        SharesModel sharesModel = sharesHistoryDataService.getModelByCodeDate(codeAll,date);

        if(sharesModel!=null&&sharesModel.getChangePer()>3) {

            float sixMean = sharesModel.getSixMean();
            float tweentyMean = sharesModel.getTweentyMean();
            float thirtyMean = sharesModel.getThirtyMean();
            float sixtyMean = sharesModel.getSixtyMean();

            float open = sharesModel.getOpen();
            float close = sharesModel.getClose();
            float high = sharesModel.getHigh();
            float low = sharesModel.getLow();

            float ocHigh = max(open, close);
            float ocLow = min(open, close);

            if (low < sixMean && low < tweentyMean && low < thirtyMean && low < sixtyMean) {
                if (close > open) {
                    //红k
                    float shk = high - ocHigh;
                    float xik = ocLow - low;
                    if (xik > shk) {
                        float oc = ocHigh - ocLow;
                        float xikPer = xik / oc;
                        if (xikPer > 0.5&&xikPer<2) {
                            //下影线 高于 开盘收盘差
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private float min(float num1,float num2){
        if(num1>num2){
            return num2;
        }else{
            return num1;
        }
    }
    private float max(float num1,float num2){
        if(num1>num2){
            return num1;
        }else{
            return num2;
        }
    }
}
