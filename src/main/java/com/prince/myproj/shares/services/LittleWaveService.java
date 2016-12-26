package com.prince.myproj.shares.services;

import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.myproj.shares.models.SingleWaveModel;
import com.prince.myproj.shares.models.WaveModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zidong.wang on 2016/12/22.
 */
@Service
public class LittleWaveService {
    public static final Logger logger = Logger.getLogger(LittleWaveService.class);

    @Autowired
    private SharesHistoryDataService sharesHistoryDataService;

    public Map<String,List<SingleWaveModel>> beginWave(WaveModel waveModel){
        Map<String,List<SingleWaveModel>> returnMap = new HashMap<String, List<SingleWaveModel>>();


        //根据起始日期 和 代码 选出一个数组
        String codes = waveModel.getShareCode();
        String start = waveModel.getStartDate();
        String end = waveModel.getEndDate();
        Map<String,List<SharesModel>> sharesModelsMap = getSharesByCodeAndDate(codes, start, end);

        Set set = sharesModelsMap.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()){
            String code = it.next();
            List<SharesModel> sharesModels = sharesModelsMap.get(code);

            if(sharesModels!=null){
                for(int i=0;i<sharesModels.size();i++){
                    SharesModel sharesModel = sharesModels.get(i);
                    logger.info(sharesModel.toString());
                }
            }

            //首先投入一半的资金，然后进行策略迭代
            List<SingleWaveModel> singleWaveModels = cacularByWave(waveModel, sharesModels);
            returnMap.put(code,singleWaveModels);
        }
        return returnMap;

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
        float initPercent = waveModel.getInitPercent();
        float payMoney = initMoney*initPercent;
        float buyPrice = sharesModel.getOpen();
        int buyNum = (int)(payMoney/buyPrice/100);
        float useMoney = buyNum*100*buyPrice;
        float surplusMoney = initMoney-useMoney;

        float nextBuyPrice = buyPrice-waveModel.getWaveSwing();
        float nextSellPrice = buyPrice+waveModel.getWaveSwing();

        singleWaveModel.setCurrentShareNum(buyNum);
        singleWaveModel.setLiveMoney(surplusMoney);
        singleWaveModel.setSharePrice(sharesModel.getClose());
        singleWaveModel.setCbMoney(useMoney);
        singleWaveModel.cacuAll();
        singleWaveModel.setBuyPrice(nextBuyPrice);
        singleWaveModel.setSellPrice(nextSellPrice);
        singleWaveModel.setToday(sharesModel.getDate());

        return singleWaveModel;
    }
    private SingleWaveModel appendBuy(WaveModel waveModel,SharesModel sharesModel,SingleWaveModel preSingleWaveModel){
        SingleWaveModel singleWaveModel = preSingleWaveModel.clonePre();

        float closePrice = sharesModel.getClose();
        float lowPrice = sharesModel.getLow();
        float highPrice = sharesModel.getHigh();

        int littleShareNum = waveModel.getWaveShareNum();
        float waveSwing = waveModel.getWaveSwing();
        logger.info("收盘价："+closePrice+" 最高价："+highPrice+" 最低价："+lowPrice);
        //模拟
        if(highPrice-closePrice>closePrice-lowPrice){
            //上坡
            while(highPrice>singleWaveModel.getSellPrice()){
                logger.info("上坡 卖");
                if(!singleWaveModel.sell(littleShareNum, waveSwing)){
                    break;
                }
            }
            while(closePrice<singleWaveModel.getBuyPrice()){
                logger.info("上坡 买");
                if(!singleWaveModel.buy(littleShareNum, waveSwing)){
                    break;
                }
            }
        }else{
            //下坡
            while(lowPrice<singleWaveModel.getBuyPrice()){
                logger.info("下坡 买");
                if(!singleWaveModel.buy(littleShareNum,waveSwing)){
                    break;
                }
            }
            while(closePrice>singleWaveModel.getSellPrice()){
                logger.info("下坡 卖");
                if(!singleWaveModel.sell(littleShareNum,waveSwing)){
                    break;
                }
            }
        }
        singleWaveModel.setSharePrice(sharesModel.getClose());
        singleWaveModel.cacuAll();
        singleWaveModel.setToday(sharesModel.getDate());
        logger.info("单日结束：" + singleWaveModel.toString());
        return singleWaveModel;
    }
    private Map<String,List<SharesModel>> getSharesByCodeAndDate(String codes,String start,String end){
        Map<String,List<SharesModel>> returnMap = new HashMap<String, List<SharesModel>>();

        String[] codeArray = codes.split(",");
        for(int i=0;i<codeArray.length;i++) {
            String code = codeArray[i];
            List<SharesModel> sharesModels = null;
            List<SharesSingleModel> singleModels = sharesHistoryDataService.getSharesModels(code);
            if (singleModels.size() > 0) {
                SharesSingleModel singleModel = singleModels.get(0);
                sharesModels = sharesHistoryDataService.getModelsByStartEndDate(singleModel.getCodeAll(), start, end);
                returnMap.put(code,sharesModels);
            }
        }
        return returnMap;
    }
}
