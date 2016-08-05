package com.prince.myproj.shares.services;

import com.prince.myproj.shares.celue.SelectSharesCelue;
import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.models.SharesSingleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2016/8/1.
 */

public class SelectSharesService {

    private List<SelectSharesCelue> selectShares;

    @Autowired
    private SharesHistoryDataService sharesHistoryDataService;

    public Map<String,List<SharesModel>> selectSharesList(String date){
        List<SharesSingleModel> allCodes = sharesHistoryDataService.getSharesModelsWithOutSC(0, 3000);

        Map<String,List<SharesModel>> resultMap = new HashMap<String, List<SharesModel>>();


        for(int j=0;j<selectShares.size();j++){
            SelectSharesCelue selectCelue = selectShares.get(j);
            List<SharesModel> cacularResult = new ArrayList<SharesModel>();
            int size = allCodes.size();
            for(int i=0;i<size;i++){
                String code = allCodes.get(i).getCodeAll();
                SharesModel sharesModel = selectCelue.select(code,date);
                if(sharesModel!=null){
                    cacularResult.add(sharesModel);
                }
            }
            SharesModel szCode = sharesHistoryDataService.getModelByCodeDate("sz399001", date);
            if(szCode!=null){
                cacularResult.add(0,szCode);
            }
            SharesModel shCode = sharesHistoryDataService.getModelByCodeDate("sh000001", date);
            if(shCode!=null){
                cacularResult.add(0,shCode);
            }
            resultMap.put(selectCelue.getName(),cacularResult);
        }
        return resultMap;
    }

    public List<SelectSharesCelue> getSelectShares() {
        return selectShares;
    }

    public void setSelectShares(List<SelectSharesCelue> selectShares) {
        this.selectShares = selectShares;
    }
}
