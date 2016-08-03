package com.prince.myproj.shares.services;

import com.prince.myproj.shares.dao.SharesHistoryDao;
import com.prince.myproj.shares.dao.SharesTempDao;
import com.prince.myproj.shares.models.*;
import com.prince.myproj.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zidong.wang on 2016/5/23.
 */
@Service
public class ShareAnalysisService {
    public static final Logger logger = Logger.getLogger(ShareAnalysisService.class);

    @Autowired
    private SharesHistoryDataService sharesHistoryDataService;

    @Autowired
    private SharesHistoryDao sharesHistoryDao;

    @Autowired
    private SharesTempDao sharesTempDao;

    @Autowired
    private DateUtil dateUtil;

    //找出今天比均线低的代码
    public void findListLowMean(){

    }
    //找出今天比均线高的代码
    public void findListHighMean(){

    }
    //找出今天涨幅很大 且量增的 （放量涨）
    public void findListHighIncreasePer(){

    }

    //找出超过备选涨幅的列表
    public List<SharesModel> findIncreaseHigherList(float per,String date){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("high",per);
        paramMap.put("date", date);
        return sharesHistoryDao.selectWithHighLow(paramMap);
    }

    public List<SharesModel> findIncreaseLowerList(float per,String date){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("low", per);
        paramMap.put("date", date);
        return sharesHistoryDao.selectWithHighLow(paramMap);
    }
    //找出cys超跌的股票
    public List<SharesModel> findCysList(String key,float val,String date){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put(key, val);
        paramMap.put("date", date);
        return sharesHistoryDao.selectWithHighLow(paramMap);
    }

    //找出cys超跌的股票
    public List<SharesModel> findCysPreList(String key,float val){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put(key, val);
        return sharesTempDao.selectWithHighLow(paramMap);
    }


    //获取大盘指标今日的股指
    public List<SharesModel> getZhiModels(){
        List<SharesModel> zhiModels = new ArrayList<SharesModel>();
        String[] codeAlls = "sh000001,sz399001,sz399006".split(",");
        for(int i=0;i<codeAlls.length;i++){
            String code = codeAlls[i];
            Map<String,Object> paramMap = new HashMap<String, Object>();
            paramMap.put("code",code);
            SharesModel lastModel = sharesHistoryDao.selectLastModel(paramMap);
            zhiModels.add(lastModel);
        }
        return zhiModels;
    }
    //找出对应model 未来几天的数据
    public Map<String,List<SharesModel>> findLowCysSomeDayData(List<SharesModel> models,int day){
        Map<String,List<SharesModel>> resultMap = new HashMap<String, List<SharesModel>>();

        int size = models.size();
        for(int i=0;i<size;i++){
            SharesModel model = models.get(i);
            String code = model.getCode();
            String startDate = model.getDate();
            String endDate = dateUtil.getAddDate(startDate, "yyyy-MM-dd", 15 * 24 * 3600 * 1000);

            Map<String,Object> paramMap = new HashMap<String, Object>();
            paramMap.put("codeAll",code);
            paramMap.put("startDate",startDate);
            paramMap.put("endDate",endDate);
            List<SharesModel> modelList = sharesHistoryDao.selectWithDate(paramMap);
            resultMap.put(code, modelList);
        }

        return resultMap;
    }

    /**
     * 根据codes 查出每只最近day的数据
     * @param codes
     * @param day
     * @return
     */
    public Map<String,List<SharesModel>> giveMeCysModelList(String[] codes,int day){
        Map<String,List<SharesModel>> resultMap = new HashMap<String, List<SharesModel>>();

        for(String code:codes){
            List<SharesModel> codeModels = getSharesListByCodeDay(code,day);
            resultMap.put(code,codeModels);
        }

        return resultMap;
    }

    private List<SharesModel> getSharesListByCodeDay(String code,int day){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code",code);
        paramMap.put("day", day);
        paramMap.put("volume",1);
        List<SharesModel> codeModels = sharesHistoryDao.selectModelByCode(paramMap);
        return codeModels;
    }

    public List<SharesModel> getSharesListByCodeDay(String code,int day,String date){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code",code);
        paramMap.put("day", day);
        paramMap.put("date",date);
        paramMap.put("volume",1);
        List<SharesModel> codeModels = sharesHistoryDao.selectModelByCode(paramMap);
        return codeModels;
    }

    private List<SharesModel> getSharesListByCodeDay(String code,Integer day,String beginDate ,String date){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code",code);
        paramMap.put("day", day);
        paramMap.put("beginDate",beginDate);
        paramMap.put("date",date);
        paramMap.put("volume",1);
        List<SharesModel> codeModels = sharesHistoryDao.selectModelByCode(paramMap);
        return codeModels;
    }

    /**
     * 找出每只股票的盈亏率最高和最低的点，还有距离
     * @return
     */
    public Map<String,AnalysisBean> findLowestCysAndT(String[] codes,Map<String,List<SharesModel>> cysModelListMap){
        Map<String,AnalysisBean> resultMap = new HashMap<String, AnalysisBean>();
        for(String code:codes){
            List<SharesModel> models = cysModelListMap.get(code);
            Collections.reverse(models);
            AnalysisBean analysisBean = analysisCysSharesList(models);
            resultMap.put(code,analysisBean);
        }
        return resultMap;
    }


    private AnalysisBean analysisCysSharesList(List<SharesModel> models){
        float lowestCys = 100;
        float highestCys = -100;
        String highestDate = "";
        String lowestDate = "";
        String zfDate = "";
        float lastCys = -100;
        int minIndex = 0;
        int maxIndex = 0;


        for(int i=0;i<models.size();i++){
            SharesModel model = models.get(i);
            float cys13 = model.getCys13();
            if (cys13>highestCys){
                highestCys = cys13;
                highestDate = model.getDate();
                maxIndex = i;
            }
            if(cys13<lowestCys){
                lowestCys = cys13;
                lowestDate = model.getDate();
                minIndex = i;
            }
            if(lastCys>0&&cys13<0){
                zfDate = model.getDate();
            }
            lastCys = cys13;
        }

        AnalysisBean analysisBean = new AnalysisBean();
        analysisBean.setHighestCys(highestCys);
        analysisBean.setHighestDate(highestDate);
        analysisBean.setLowestCys(lowestCys);
        analysisBean.setLowestDate(lowestDate);
        analysisBean.setZfDate(zfDate);
        analysisBean.setT(Math.abs(maxIndex-minIndex));
        return analysisBean;

    }

    /**
     * 分析量价和成本之间的关系
     * 使用全量统计
     * 分析出 放量|缩量 昨日收盘低于cyc13且今日收盘高于cyc13|昨日收盘高于cyc13且今日收盘低于cyc13 之后两日的涨跌
     *
     */
    public void analysisVolumeCyc(){
        //先拿到code
        List<SharesSingleModel> codeList = sharesHistoryDataService.getSharesModels(0,3000);
        int size = codeList.size();
        Map<String,List<AnalysisVolumeCycBean>> tjMap = new HashMap<String, List<AnalysisVolumeCycBean>>();
        for(int i=0;i<size;i++){
            SharesSingleModel sharesSingleModel = codeList.get(i);
            List<AnalysisVolumeCycBean> analysisList = analysisVolumeCycByCode(sharesSingleModel.getCodeAll());
            tjMap.put(sharesSingleModel.getCodeAll(),analysisList);
        }
        analysisResult(tjMap);
    }

    private void analysisResult(Map<String,List<AnalysisVolumeCycBean>> tjMap){
        Set<String> keySet = tjMap.keySet();
        Iterator<String> it = keySet.iterator();

        int allNum = 0;//样本总数
        int fzzNum = 0;//放量超越成本线且两日涨
        int fzdNum = 0;//放量超越成本线且两日跌
        int szzNum = 0;//缩量超越成本线且两日涨
        int szdNum = 0;//缩量超越成本线且两日涨
        int sdzNum = 0;//缩量跌下成本线且两日涨
        int sddNum = 0;//缩量跌下成本线且两日跌
        int fdzNum = 0;//放量跌下成本线且两日涨
        int fddNum = 0;//放量跌下成本线且两日跌


        while (it.hasNext()){
            int allItemNum = 0;//样本总数
            int fzzItemNum = 0;//放量超越成本线且两日涨
            int fzdItemNum = 0;//放量超越成本线且两日跌
            int szzItemNum = 0;//缩量超越成本线且两日涨
            int szdItemNum = 0;//缩量超越成本线且两日涨
            int sdzItemNum = 0;//缩量跌下成本线且两日涨
            int sddItemNum = 0;//缩量跌下成本线且两日跌
            int fdzItemNum = 0;//放量跌下成本线且两日涨
            int fddItemNum = 0;//放量跌下成本线且两日跌

            String code = it.next();
            List<AnalysisVolumeCycBean> analysisList = tjMap.get(code);
            int size = analysisList.size();
            for(int i=0;i<size;i++){
                AnalysisVolumeCycBean analysisVolumeCycBean = analysisList.get(i);
                allItemNum++;
                boolean f = analysisVolumeCycBean.isLargerVol();
                boolean h = analysisVolumeCycBean.isHigherCyc();
                boolean inc = analysisVolumeCycBean.isAfterTomIncrease();
//                boolean inc = analysisVolumeCycBean.isTomIncrease();
                if(f&&h&&inc){
                    fzzItemNum++;
                }
                if(f&&h&&!inc){
                    fzdItemNum++;
                }
                if(!f&&h&&inc){
                    szzItemNum++;
                }
                if(!f&&h&&!inc){
                    szdItemNum++;
                }
                if(!f&&!h&&inc){
                    sdzItemNum++;
                }
                if(!f&&!h&&!inc){
                    sddItemNum++;
                }
                if(f&&!h&&inc){
                    fdzItemNum++;
                }
                if(f&&!h&&!inc){
                    fddItemNum++;
                }
            }

            logger.info("code:"+code+"--样本数:"+allItemNum
                        +"--放量涨涨:"+fzzItemNum
                        +"--放量涨跌:"+fzdItemNum
                        +"--放量跌涨:"+fdzItemNum
                        +"--放量跌跌:"+fddItemNum
                        +"--缩量涨涨:"+szzItemNum
                        +"--缩量涨跌:"+szdItemNum
                        +"--缩量跌跌:"+sddItemNum
                        +"--缩量跌涨:"+sdzItemNum
                        );

            allNum += allItemNum;
            fzzNum += fzzItemNum;
            fzdNum += fzdItemNum;
            szzNum += szzItemNum;
            szdNum += szdItemNum;
            sdzNum += sdzItemNum;
            sddNum += sddItemNum;
            fdzNum += fdzItemNum;
            fddNum += fddItemNum;
        }
        logger.info("样本总数:" + allNum
                        + "--放量涨涨:" + fzzNum
                        + "--放量涨跌:" + fzdNum
                        + "--放量跌涨:" + fdzNum
                        + "--放量跌跌:" + fddNum
                        + "--缩量涨涨:" + szzNum
                        + "--缩量涨跌:" + szdNum
                        + "--缩量跌跌:" + sddNum
                        + "--缩量跌涨:" + sdzNum
        );

    }

    public List<AnalysisVolumeCycBean> analysisVolumeCycByCode(String code){
        //拿出最近60个交易日的数据
        List<SharesModel> codeModels = getSharesListByCodeDay(code, 60);
        List<AnalysisVolumeCycBean> analysisVolumeCycBeans = new ArrayList<AnalysisVolumeCycBean>();
        int size = codeModels.size();
        for(int i=0;i+3<size;i++){
            SharesModel yestoday = codeModels.get(i);
            SharesModel today = codeModels.get(i + 1);
            SharesModel tomorow = codeModels.get(i+2);
            SharesModel afterTom = codeModels.get(i+3);

            AnalysisVolumeCycBean analysisVolumeCycBean = null;
            float volper = today.getVolume()/yestoday.getVolume();
            float yuper = 0.2f;//放量 缩量的阈值
            if(yestoday.getClose()<yestoday.getCyc13()&&today.getClose()>today.getCyc13() && (volper>(1+yuper)||volper<(1-yuper))){
                analysisVolumeCycBean = new AnalysisVolumeCycBean();
                analysisVolumeCycBean.setIsHigherCyc(true);
            }
            if(yestoday.getClose()>yestoday.getCyc13()&&today.getClose()<today.getCyc13() && (volper>(1+yuper)||volper<(1-yuper)) ){
                analysisVolumeCycBean = new AnalysisVolumeCycBean();
                analysisVolumeCycBean.setIsHigherCyc(false);
            }

            if(analysisVolumeCycBean!=null){
                analysisVolumeCycBean.setDesModel(today);
                if(volper>(1+yuper)){
                    analysisVolumeCycBean.setLargerVol(true);
                }else{
                    analysisVolumeCycBean.setLargerVol(false);
                }
                analysisVolumeCycBean.setAfterTomIncreasePer(tomorow.getIncreasePer());
                analysisVolumeCycBean.setIsTomIncrease(tomorow.getIncreasePer() > 0);
                analysisVolumeCycBean.setIsAfterTomIncrease(afterTom.getClose() > today.getClose());
                analysisVolumeCycBean.setAfterTomIncreasePer((afterTom.getClose()-today.getClose())/today.getClose());

                analysisVolumeCycBeans.add(analysisVolumeCycBean);
            }
        }

        return analysisVolumeCycBeans;
    }


    /**
     * 分析量与中期cys之间的关系
     */
    public void analysisVolumeCys(){
        //先拿到code
        List<SharesSingleModel> codeList = sharesHistoryDataService.getSharesModels(0, 3000);
        int size = codeList.size();
        Map<String,List<AnalysisVolumeCycBean>> tjMap = new HashMap<String, List<AnalysisVolumeCycBean>>();
        for(int i=0;i<size;i++){
            SharesSingleModel sharesSingleModel = codeList.get(i);
            List<AnalysisVolumeCycBean> analysisList = analysisVolumeCysByCode(sharesSingleModel.getCodeAll());
            tjMap.put(sharesSingleModel.getCodeAll(),analysisList);
        }
        analysisResult(tjMap);
    }

    public List<AnalysisVolumeCycBean> analysisVolumeCysByCode(String code){
        List<SharesModel> codeModels = getSharesListByCodeDay(code, 60);
        List<AnalysisVolumeCycBean> analysisVolumeCycBeans = new ArrayList<AnalysisVolumeCycBean>();
        int size = codeModels.size();
        return analysisVolumeCycBeans;

    }


    /**
     * 分析买入策略
     * 分析60日内股票
     * 选择cys最低点的后一日开盘买入
     * 记录当日买入的cys标准 与最近一个交易日做比较 计算营收
     */
    public List<AnalysisBuyTimeBean> analysisBuyTime(int day,int maxWaitDay,float inc){
        List<SharesSingleModel> codeList = sharesHistoryDataService.getSharesModelsWithOutSC(0, 3000);
        int size = codeList.size();
        List<String> codes = new ArrayList<String>();
        for(int i=0;i<size;i++){
            codes.add(codeList.get(i).getCodeAll());
        }
        return analysisBuyTimeByCodeList(codes,day,maxWaitDay,inc);
    }

    public List<AnalysisBuyTimeBean> analysisBuyTimeByCodeList(List<String> codeList,int day,int maxWaitDay,float inc){
        List<AnalysisBuyTimeBean> analysisBuyTimeBeanList = new ArrayList<AnalysisBuyTimeBean>();
        int size = codeList.size();
        for(int i=0;i<size;i++){
            String code = codeList.get(i);
            AnalysisBuyTimeBean analysisBuyTimeBean = analysisBuyTimeOne(code ,day,maxWaitDay,inc);
            if(analysisBuyTimeBean!=null) {
                analysisBuyTimeBeanList.add(analysisBuyTimeBean);
            }
        }

        int ysize = analysisBuyTimeBeanList.size();
        logger.info("样本总数："+ysize);
        int successNum = 0;
        int fallNum = 0;
        int increaseNum = 0;
        float waitTime =0;
        for(int i=0;i<ysize;i++){
            AnalysisBuyTimeBean analysisBuyTimeBean = analysisBuyTimeBeanList.get(i);
            if(analysisBuyTimeBean.isSuccess()){
                successNum++;
                waitTime += analysisBuyTimeBean.getWaitDay();
            }else{
                fallNum++;
            }
            if(analysisBuyTimeBean.getIncreasePer()>0){
                increaseNum ++;
            }
        }
        waitTime = waitTime/increaseNum;

        logger.info("考察范围："+day+"---目标涨幅："+inc+"---容忍时间："+maxWaitDay);
        logger.info("策略成功的股:"+successNum+"---策略失败的股数："+fallNum+"---平均等待时间："+waitTime);
        logger.info("策略收涨的股:"+increaseNum);
        return analysisBuyTimeBeanList;
    }

    public AnalysisBuyTimeBean analysisBuyTimeOne(String code,int day,int maxWaitDay,float inc){
        if(code.equals("sh000001") || code.equals("sz399001") || code.equals("sz399006")){
            return null;
        }
        List<SharesModel> codeModels = getSharesListByCodeDay(code, day);
        Collections.reverse(codeModels);
        return analysisBuyTimeOne(codeModels,code,maxWaitDay,inc,true);
    }

    private AnalysisBuyTimeBean analysisBuyTimeOne(List<SharesModel> codeModels,String code,int maxWaitDay,float inc,boolean isfindmin){
        int size = codeModels.size();
        SharesModel buyModel = null;
        SharesModel minModel = null;
        SharesModel todayModel = null;
        SharesModel sellModel = null;

        if(size>0){
            todayModel = codeModels.get(size-1);
        }else {
            return null;
        }
        int minIndex = 0;
        if(isfindmin){
            for(int i=0;i<size;i++){
                SharesModel current = codeModels.get(i);
                if(minModel==null){
                    minModel = current;
                    continue;
                }
                if(minModel.getCys13()>current.getCys13()){
                    minModel = current;
                    minIndex = i;
                }
            }
        }else {
            minModel = codeModels.get(0);
        }


        if(minIndex<size-1){
            AnalysisBuyTimeBean analysisBuyTimeBean = new AnalysisBuyTimeBean();
            buyModel = codeModels.get(minIndex+1);

            float buyPrice = buyModel.getOpen();
            boolean success = false;
            int sellIndex = 0;
            for(int i=minIndex+1;i<size && i<minIndex+maxWaitDay+1;i++){
                sellModel = codeModels.get(i);
                sellIndex = i;
                float highPrice = sellModel.getHigh();
                if(highPrice>(1+inc)*buyPrice){
                    success = true;
                    break;
                }
            }


            float increaseVal = 0;
            float increasePer = 0;
            int waitDay = 0;

            analysisBuyTimeBean.setSuccess(success);
            if(success){
                increaseVal = sellModel.getHigh()-buyPrice;
                increasePer = increaseVal/buyPrice;
            }else{
                increaseVal = sellModel.getClose()-buyPrice;
                increasePer = increaseVal/buyPrice;
            }
            waitDay = sellIndex-minIndex;

            analysisBuyTimeBean.setSellModel(sellModel);
            analysisBuyTimeBean.setBuyModel(buyModel);
            analysisBuyTimeBean.setMinModel(minModel);
            analysisBuyTimeBean.setTodayModel(todayModel);

            analysisBuyTimeBean.setIncreasePer(increasePer);
            analysisBuyTimeBean.setIncreaseVal(increaseVal);
            analysisBuyTimeBean.setWaitDay(waitDay);
            analysisBuyTimeBean.setCode(code);

            return analysisBuyTimeBean;
        }
        return null;
    }

    public Map<String,AnalysisBuyTimeTotal> testRealInc(Map<String,List<SharesModel>> sharesListMap,int maxWaitDay,float inc){
        if(sharesListMap==null)return null;
        Map<String,AnalysisBuyTimeTotal> resultMap = new HashMap<String, AnalysisBuyTimeTotal>();
        Set<String> sharesKey = sharesListMap.keySet();
        Iterator<String> it = sharesKey.iterator();
        while(it.hasNext()){
            String key = it.next();
            List<SharesModel> shares = sharesListMap.get(key);
            if(shares!=null){
                AnalysisBuyTimeTotal analysisBuyTimeTotal = testRealInc(shares,maxWaitDay,inc);
                resultMap.put(key,analysisBuyTimeTotal);
            }
        }
        return resultMap;
    }

    /**
     * 查看预测的实际效果
     * @param models
     * @param maxWaitDay
     * @param inc
     * @return
     */
    public AnalysisBuyTimeTotal testRealInc(List<SharesModel> models,int maxWaitDay,float inc){
        if(models==null)return null;
        int size = models.size();
        List<AnalysisBuyTimeBean> analysisBuyTimeBeanList = new ArrayList<AnalysisBuyTimeBean>();
        float shIncPer=0;
        float szIncPer=0;
        for(int i=0;i<size;i++){
            SharesModel sharesModel = models.get(i);
            if(sharesModel.getCode().equals("sh000001")){
                shIncPer = sharesModel.getIncreasePer();
            }
            if(sharesModel.getCode().equals("sz399001")){
                szIncPer = sharesModel.getIncreasePer();
            }
            AnalysisBuyTimeBean analysisBuyTimeBean = testRealIncOne(models.get(i),maxWaitDay,inc);
            if(analysisBuyTimeBean!=null){
                analysisBuyTimeBeanList.add(analysisBuyTimeBean);
            }
        }

        int ysize = analysisBuyTimeBeanList.size();
        logger.info("样本总数："+ysize);
        int successNum = 0;
        int fallNum = 0;
        int increaseNum = 0;
        float waitTime =0;
        String date = "";
        for(int i=0;i<ysize;i++){
            AnalysisBuyTimeBean analysisBuyTimeBean = analysisBuyTimeBeanList.get(i);
            if(analysisBuyTimeBean.isSuccess()){
                successNum++;
                waitTime += analysisBuyTimeBean.getWaitDay();
            }else{
                fallNum++;
            }
            if(analysisBuyTimeBean.getIncreasePer()>0){
                increaseNum ++;
            }
            date = analysisBuyTimeBean.getTodayModel().getDate();
        }
        if(increaseNum==0){
            waitTime=0;
        }else {
            waitTime = waitTime/increaseNum;
        }

        logger.info("时间：" + date +"---上证："+(shIncPer>0)+"---深圳："+(szIncPer>0));
        logger.info("目标涨幅："+inc+"---容忍时间："+maxWaitDay);
        logger.info("预测成功的股:" + successNum + "---预测失败的股数：" + fallNum + "---平均等待时间：" + waitTime);
        logger.info("预测收涨的股:" + increaseNum);

        AnalysisBuyTimeTotal analysisBuyTimeTotal = new AnalysisBuyTimeTotal();
        analysisBuyTimeTotal.setInc(inc);
        analysisBuyTimeTotal.setShIncPer(shIncPer);
        analysisBuyTimeTotal.setSzIncPer(szIncPer);
        analysisBuyTimeTotal.setFallNum(fallNum);
        analysisBuyTimeTotal.setIncreaseNum(increaseNum);
        analysisBuyTimeTotal.setMaxWaitDay(maxWaitDay);
        analysisBuyTimeTotal.setSuccessNum(successNum);
        analysisBuyTimeTotal.setWaitTime(waitTime);
        analysisBuyTimeTotal.setAnalysisBuyTimeBeanList(analysisBuyTimeBeanList);


        return analysisBuyTimeTotal;
    }

    private AnalysisBuyTimeBean testRealIncOne(SharesModel model,int maxWaitDay,float inc){
        String code = model.getCode();
        if(code.equals("sh000001") || code.equals("sz399001") || code.equals("sz399006")){
            return null;
        }
        String beginDate = model.getDate();
        List<SharesModel> codeModels = getSharesListByCodeDay(code, null, beginDate, null);
        Collections.reverse(codeModels);
        return analysisBuyTimeOne(codeModels,code,maxWaitDay,inc,false);

    }

    public List<SharesModel> cacularBuyShares(String date,int day,float cys){
        List<SharesSingleModel> allCodes = sharesHistoryDataService.getSharesModelsWithOutSC(0, 3000);
        List<SharesModel> cacularResult = new ArrayList<SharesModel>();
        int size = allCodes.size();
        for(int i=0;i<size;i++){
            String code = allCodes.get(i).getCodeAll();
            SharesModel sharesModel = cacularBuySharesOne(code,date,day,cys);
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
        return cacularResult;
    }

    public List<SharesModel> cacularBuySharesPre(String date,int day,float cys){
        List<SharesSingleModel> allCodes = sharesHistoryDataService.getSharesModelsWithOutSC(0, 3000);
        List<SharesModel> cacularResult = new ArrayList<SharesModel>();
        int size = allCodes.size();
        for(int i=0;i<size;i++){
            String code = allCodes.get(i).getCodeAll();
            SharesModel sharesModel = cacularBuySharesOnePre(code, date, day, cys);
            if(sharesModel!=null){
                cacularResult.add(sharesModel);
            }
        }
        return cacularResult;
    }

    //选股策略
    public SharesModel cacularBuySharesOne(String code,String date ,int day,float cys){
        if(code.equals("sh000001") || code.equals("sz399001") || code.equals("sz399006")){
            return null;
        }
        List<SharesModel> codeModels = getSharesListByCodeDay(code, day, date);
        Collections.reverse(codeModels);

        int size = codeModels.size();
        if(size>0){
            SharesModel lastModel = codeModels.get(size-1);
            if (!lastModel.getDate().equals(date)){
                return null;
            }
//            for(int i=0;i<size;i++){
//                SharesModel current = codeModels.get(i);
//                if(lastModel.getCys13()>current.getCys13()){
//                    return null;
//                }
//            }
//            if(lastModel.getCys13()<cys){
//                return lastModel;
//            }
            if(size>1){
                SharesModel secondModel = codeModels.get(size-2);
                if(secondModel.getCys13()<0&&lastModel.getCys13()>0&& lastModel.getVolume()>secondModel.getVolume()*1.2){
                    int num = 0;
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

    private SharesModel cacularBuySharesOnePre(String code,String date ,int day,float cys){
        if(code.equals("sh000001") || code.equals("sz399001") || code.equals("sz399006")){
            return null;
        }
        List<SharesModel> codeModels = getSharesListByCodeDay(code, day, date);
        Collections.reverse(codeModels);

        SharesModel todayModel=null;
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code",code);
        List<SharesModel> todayModels = sharesTempDao.selectByMap(paramMap);
        if(todayModels.size()>0){
            todayModel = todayModels.get(0);
            codeModels.add(todayModel);
        }else {
            return null;
        }

        int size = codeModels.size();
        if(size>0){
            SharesModel lastModel = codeModels.get(size - 1);
            if(size>1){
                SharesModel secondModel = codeModels.get(size-2);
                if(secondModel.getCys13()<0&&lastModel.getCys13()>0&& lastModel.getVolume()>secondModel.getVolume()*1.2){
                    int num = 0;
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
}
