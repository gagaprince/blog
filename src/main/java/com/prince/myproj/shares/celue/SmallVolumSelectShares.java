package com.prince.myproj.shares.celue;

import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.services.ShareAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zidong.wang on 2016/8/1.
 */
public class SmallVolumSelectShares implements SelectSharesCelue {
    @Autowired
    private ShareAnalysisService shareAnalysisService;

    private float lowLimit = -16;


    public SharesModel select(String code, String date) {
        if(code.equals("sh000001") || code.equals("sz399001") || code.equals("sz399006")){
            return null;
        }
        List<SharesModel> codeModels = shareAnalysisService.getSharesListByCodeDay(code, 60, date);
        int size = codeModels.size();
        if(size>0){
            SharesModel lastModel = codeModels.get(size-1);
            if (!lastModel.getDate().equals(date)){
                return null;
            }
            if(size>1){
                SharesModel secondModel = codeModels.get(size-2);
                //���һ�յ�cysֵС��16 ���ڳ���   //������С��ǰһ�յ�0.8  //��
                if(lastModel.getCys13()<lowLimit&& lastModel.getVolume()<secondModel.getVolume()*0.8 && lastModel.getClose()<secondModel.getClose()){
                    int num = 0;
                    //
                    for(int i=size-2;i>=0&&i>=size-10;i--){
                        SharesModel tempModel = codeModels.get(i);
                        if (tempModel.getCys13()<0){
                            num++;
                        }
                    }
                    if(num>5){
                        return lastModel;
                    }
                }
            }
        }
        return null;
    }

    public String getName() {
        return "suo liang die ce lue";
    }
}
