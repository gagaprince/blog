package com.prince.myproj.shares.services;

import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.myproj.shares.models.SingleWaveModel;
import com.prince.myproj.shares.models.WaveModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zidong.wang on 2016/12/22.
 */
@Service
public class LittleWaveService {
    public static final Logger logger = Logger.getLogger(LittleWaveService.class);

    @Autowired
    private SharesHistoryDataService sharesHistoryDataService;

    public void beginWave(WaveModel waveModel){
        //根据起始日期 和 代码 选出一个数组
        String code = waveModel.getShareCode();
        String start = waveModel.getStartDate();
        String end = waveModel.getEndDate();
        List<SharesModel> sharesModels = getSharesByCodeAndDate(code, start, end);

        if(sharesModels!=null){
            for(int i=0;i<sharesModels.size();i++){
                SharesModel sharesModel = sharesModels.get(i);
                logger.info(sharesModel.toString());
            }
        }

        //首先投入一半的资金，然后进行策略迭代
        List<SingleWaveModel> singleWaveModels = cacularByWave(waveModel,sharesModels);


    }
    private List<SingleWaveModel> cacularByWave(WaveModel waveModel ,List<SharesModel> sharesModels){
        List<SingleWaveModel> singleWaveModels = new ArrayList<SingleWaveModel>();
        int size = sharesModels.size();
        for(int i=0;i<size;i++){
            SharesModel sharesModel = sharesModels.get(i);
            SingleWaveModel singleWaveModel = null;
            if(i==0){
                singleWaveModel = initBuy(waveModel,sharesModel);
            }else{
                singleWaveModel = appendBuy(waveModel,sharesModel,singleWaveModels.get(i-1));
            }
            singleWaveModels.add(singleWaveModel);
        }
        return singleWaveModels;
    }
    private SingleWaveModel initBuy(WaveModel waveModel,SharesModel sharesModel){
        //首次买入 以开始价钱买入 使用资金的一半
        SingleWaveModel singleWaveModel = new SingleWaveModel();

        float initMoney = waveModel.getInitMoney();
        float halfMoney = initMoney/2;
        float buyPrice = sharesModel.getOpen();
        int buyNum = (int)(halfMoney/buyPrice/100);
        float useMoney = buyNum*100*buyPrice;
        float surplusMoney = initMoney-useMoney;

        float nextBuyPrice = buyPrice-waveModel.getWaveSwing();
        float nextSellPrice = buyPrice+waveModel.getWaveSwing();

        singleWaveModel.setCurrentShareNum(buyNum);
        singleWaveModel.setLiveMoney(surplusMoney);
        singleWaveModel.setSharePrice(sharesModel.getClose());
        singleWaveModel.cacuAll();
        singleWaveModel.setBuyPrice(nextBuyPrice);
        singleWaveModel.setSellPrice(nextSellPrice);

        return singleWaveModel;
    }
    private SingleWaveModel appendBuy(WaveModel waveModel,SharesModel sharesModel,SingleWaveModel preSingleWaveModel){
        SingleWaveModel singleWaveModel = preSingleWaveModel.clonePre();

        float buyPrice = singleWaveModel.getBuyPrice();
        float sellPrice = singleWaveModel.getSellPrice();

        float closePrice = sharesModel.getClose();
        float lowPrice = sharesModel.getLow();

        int littleShareNum = waveModel.getWaveShareNum();

        int currentShareNum = singleWaveModel.getCurrentShareNum();
        //可以卖出的股票数量
        int newBuyShareNum = singleWaveModel.getNewBuyShareNum();
        float surplusMoney = singleWaveModel.getLiveMoney();

        //模拟买入
        while (lowPrice<buyPrice){
            //可以买入
            float useMoney = buyPrice*littleShareNum*100;
            float surplusMoneyTemp = surplusMoney-useMoney;
            if(surplusMoneyTemp>0){
                //买入成功
                currentShareNum+=littleShareNum;
                surplusMoney = surplusMoneyTemp;
            }
        }
        if(closePrice>sellPrice){
            //可以卖出
            if(currentShareNum>littleShareNum){
                currentShareNum-=littleShareNum;
                surplusMoney+=sellPrice*littleShareNum*100;
            }
        }




        return singleWaveModel;
    }
    private List<SharesModel> getSharesByCodeAndDate(String codes,String start,String end){
        List<SharesModel> sharesModels = null;
        List<SharesSingleModel> singleModels = sharesHistoryDataService.getSharesModels(codes);
        if(singleModels.size()>0){
            SharesSingleModel singleModel = singleModels.get(0);
            sharesModels = sharesHistoryDataService.getModelsByStartEndDate(singleModel.getCodeAll(), start, end);
        }
        return sharesModels;
    }
}
