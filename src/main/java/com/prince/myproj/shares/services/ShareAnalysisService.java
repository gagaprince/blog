package com.prince.myproj.shares.services;

import com.prince.myproj.shares.dao.SharesHistoryDao;
import com.prince.myproj.shares.dao.SharesTempDao;
import com.prince.myproj.shares.models.AnalysisVolumeCycBean;
import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.models.AnalysisBean;
import com.prince.myproj.shares.models.SharesSingleModel;
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
        List<SharesModel> codeModels = getSharesListByCodeDay(code,60);
        List<AnalysisVolumeCycBean> analysisVolumeCycBeans = new ArrayList<AnalysisVolumeCycBean>();
        int size = codeModels.size();
        for(int i=0;i+3<size;i++){
            SharesModel yestoday = codeModels.get(i);
            SharesModel today = codeModels.get(i+1);
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
        List<SharesModel> codeModels = getSharesListByCodeDay(code,60);
        List<AnalysisVolumeCycBean> analysisVolumeCycBeans = new ArrayList<AnalysisVolumeCycBean>();
        int size = codeModels.size();
        return analysisVolumeCycBeans;

    }


}
