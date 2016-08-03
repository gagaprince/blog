package com.prince.myproj.shares.celue;

import com.prince.myproj.shares.mathutils.MathBasicFun;
import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.services.ShareAnalysisService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zidong.wang on 2016/8/1.
 */
public class SmallVolumSelectShares implements SelectSharesCelue {
    public static final Logger logger = Logger.getLogger(SmallVolumSelectShares.class);
    @Autowired
    private ShareAnalysisService shareAnalysisService;

    private float lowLimit = -16;


    public SharesModel select(String code, String date) {
        if(code.equals("sh000001") || code.equals("sz399001") || code.equals("sz399006")){
            return null;
        }
        List<SharesModel> codeModels = shareAnalysisService.getSharesListByCodeDay(code, 60, date);
        Collections.reverse(codeModels);
        int size = codeModels.size();
        if(size>0){
            SharesModel lastModel = codeModels.get(size-1);
            if (!lastModel.getDate().equals(date)){
                return null;
            }
            if(size>1){
                SharesModel secondModel = codeModels.get(size-2);
                //最后一日的cys值小于16 属于超跌   //交易量小于前一日的0.8  //跌
//                if(lastModel.getCys13()<lowLimit&& lastModel.getVolume()<secondModel.getVolume()*0.9 && lastModel.getClose()<secondModel.getClose()){
                if(lastModel.getCys13()<lowLimit&&lastModel.getVolume()<secondModel.getVolume()*0.9){
                    logger.info(lastModel.getCode()+"----"+lastModel.getCys13());
                    /*List<Float> closeS = new ArrayList<Float>();
                    for(int i=size-2;i>=0&&i>=size-10;i--){
                        SharesModel tempModel = codeModels.get(i);
                        closeS.add(tempModel.getClose());
                    }
                    MathBasicFun mathBasicFun = new MathBasicFun(closeS);
                    mathBasicFun.excute();
                    float mean = mathBasicFun.getMean();
                    float range = mathBasicFun.getRange();
                    logger.info(lastModel.getCode()+"----"+mean+"----"+range);
                    //极差振幅不超过3%认为是横盘 地位横盘10个交易日 有机会商鞅
                    logger.info(lastModel.getCode()+"----"+range/mean);
                    if(range/mean<0.03){
                        return lastModel;
                    }*/
                    return lastModel;
                }
            }
        }
        return null;
    }

    public String getName() {
        return "suo liang die ce lue";
    }
}
