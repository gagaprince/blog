package com.prince.myproj.shares.services;

import com.prince.myproj.shares.models.KModel;
import com.prince.myproj.shares.models.LHBCacularResult;
import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.myproj.util.DateUtil;
import com.prince.myproj.weixin.bean.ShareModel;
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
    @Autowired
    public DragonTigerService dragonTigerService;

    public List<SharesSingleModel> findSharesByK(String date){
        int start = 0;
        int end = 3000;
        List<SharesSingleModel> sharesSingleModels = sharesHistoryDataService.getSharesModels(start, end);

        List<SharesSingleModel> selectShares=new ArrayList<SharesSingleModel>();


        for(int i=0;i<sharesSingleModels.size();i++){
            SharesSingleModel sharesSingleModel = sharesSingleModels.get(i);

            SharesModel sharesModel = judgeSharesSingleByK(sharesSingleModel, date);
            if(sharesModel!=null){
                SharesModel preSharesModel = getYesTodayShares(sharesSingleModel.getCodeAll(),date);
                if(preSharesModel==null){
                    continue;
                }
                SharesModel prePreSharesModel = getYesTodayShares(sharesSingleModel.getCodeAll(), preSharesModel.getDate());
                if(preSharesModel!=null && prePreSharesModel != null) {
//                    if (preSharesModel.getIncreasePer() > 0 && prePreSharesModel.getIncreasePer()<0) {
//                        float vk = prePreSharesModel.getVolume()/preSharesModel.getVolume();
//                        if(vk>0.8) {//量没有降~
////                            selectShares.add(sharesSingleModel);
//                        }
//                    }
                    float currentSixMean = sharesModel.getSixMean();
                    float preSixMean = preSharesModel.getSixMean();
                    float sixMeanIncrease = (currentSixMean-preSixMean)/preSixMean;
                    if(sixMeanIncrease>0.01 && sharesModel.getClose()>9) {
                        logger.info("备选："+sharesSingleModel.getCodeAll()+" "+sharesSingleModel.getName());

                        logger.info("前一日涨幅：" + preSharesModel.getIncreasePer());
                        logger.info("更前一日涨幅："+prePreSharesModel.getIncreasePer());

                        logger.info("前一日的量："+prePreSharesModel.getVolume());
                        logger.info("更前一日的量："+preSharesModel.getVolume());

                        logger.info("--------------------------");
                        selectShares.add(sharesSingleModel);
                    }
                }
            }
        }
        return selectShares;
    }

    private SharesModel getYesTodayShares(String code,String date){

        String preStartDate = dateUtil.getAddDate(date, "yyyy-MM-dd", -24 * 5 * 3600000);
        String preEndDate = dateUtil.getAddDate(date, "yyyy-MM-dd", -24 * 3600000);


        List<SharesModel> preSharesModels = sharesHistoryDataService.getModelsByStartEndDate(code, preStartDate, preEndDate);
        SharesModel preSharesModel = null;
        if(preSharesModels.size()>0){
            preSharesModel = preSharesModels.get(preSharesModels.size()-1);
        }
        return preSharesModel;
    }

    private SharesModel judgeSharesSingleByK(SharesSingleModel sharesSingleModel,String date){

        String code = sharesSingleModel.getCode();
        if(code.startsWith("30")){
            return null;
        }
        if(code.equals("000001")||code.equals("399001")||code.equals("399006")){
            return null;
        }
        String codeAll = sharesSingleModel.getCodeAll();

        SharesModel sharesModel = sharesHistoryDataService.getModelByCodeDate(codeAll, date);

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

            if (low < sixMean && low < tweentyMean && high>sixMean) {
                if (close > open) {
                    //红k
                    float shk = high - ocHigh;
                    float xik = ocLow - low;
                    if (xik > shk) {
                        float oc = ocHigh - ocLow;
                        float xikPer = xik / oc;
                        float ocPer = oc/close;
                        if (xikPer > 0.4&&xikPer<3 ) {
                            //下影线 高于 开盘收盘差
                            return sharesModel;
                        }
                    }
                }
            }
        }
        return null;
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

    public List<LHBCacularResult> caculateSuccessPer(String date,String dayNumStr){
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
            List<SharesSingleModel> singleModels = findSharesByK(date);

            lhbCacularResults.addAll(dragonTigerService.validateCaculate(singleModels, date));
        }
        return lhbCacularResults;
    }

    public List<SharesSingleModel> selectSharesByThreeK(String date){
        int start = 0;
        int end = 3000;
        List<SharesSingleModel> sharesSingleModels = sharesHistoryDataService.getSharesModels(start, end);
        List<SharesSingleModel> selectShares=new ArrayList<SharesSingleModel>();
        for(int i=0;i<sharesSingleModels.size();i++){
            SharesSingleModel sharesSingleModel = sharesSingleModels.get(i);
            if(selectBySingleAndDate(sharesSingleModel,date)){
                selectShares.add(sharesSingleModel);
            }
        }
        return selectShares;
    }

    private boolean selectBySingleAndDate(SharesSingleModel sharesSingleModel,String date){
        String code = sharesSingleModel.getCode();
        String codeAll = sharesSingleModel.getCodeAll();
        if(code.startsWith("30")){
            return false;
        }
        if(code.equals("000001")||code.equals("399001")||code.equals("399006")){
            return false;
        }
        List<SharesModel> selectSharesModels = giveMeLastForDaysSharesModel(codeAll,date);
        if(selectSharesModels!=null&&selectSharesModels.size()==4){
            List<KModel> kModels = parseKModelList(selectSharesModels);
            KModel kModel1 = kModels.get(0);
            //首绿
            if(!kModel1.getColor()){
                KModel kModel2 = kModels.get(1);
                KModel kModel3 = kModels.get(2);
                KModel kModel4 = kModels.get(3);
                //全红
                if(kModel2.getColor()&&kModel3.getColor()&&kModel4.getColor()){
                    //量增
                    if(kModel2.getVolum()<kModel4.getVolum()&&kModel3.getVolum()<kModel4.getVolum()){
                        //5日线上升
                        float lastSixMean = kModel4.getSixMean();
                        float firstSixMean = kModel1.getSixMean();
                        float sixMean2 = kModel2.getSixMean();
                        float sixMean3 = kModel3.getSixMean();
                        if(cacularAddPer(firstSixMean,lastSixMean)>0.01
                                &&cacularAddPer(sixMean2,lastSixMean)>0.005 && cacularAddPer(sixMean3,lastSixMean)>0.005){
                            logger.info("选出："+sharesSingleModel.getCodeAll()+" "+sharesSingleModel.getName());
                            if(kModel4.getClose()>9){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private float cacularAddPer(float pre,float next){
        return (next-pre)/pre;
    }

    private List<KModel> parseKModelList(List<SharesModel> sharesModels){
        List<KModel> kModels = new ArrayList<KModel>();
        for(int i=0;i<sharesModels.size();i++){
            SharesModel sharesModel = sharesModels.get(i);
            kModels.add(parseKModel(sharesModel));
        }

        return kModels;
    }

    private KModel parseKModel(SharesModel sharesModel){
        KModel kModel = new KModel();
        kModel.setAllBySharesModel(sharesModel);
        return kModel;
    }

    private List<SharesModel> giveMeLastForDaysSharesModel(String code,String date){
        String startDate = dateUtil.getAddDate(date,"yyyy-MM-dd",-24*10*3600000);
        List<SharesModel> sharesModels = sharesHistoryDataService.getModelsByStartEndDate(code, startDate, date);

        List<SharesModel> selectSharesModels = new ArrayList<SharesModel>();

        int size = sharesModels.size();
        if(size>=4){
            for(int i=4;i>0;i--){
                selectSharesModels.add(sharesModels.get(size-i));
            }
        }else{
            return null;
        }
        return selectSharesModels;

    }
}
