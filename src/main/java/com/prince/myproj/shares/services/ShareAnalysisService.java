package com.prince.myproj.shares.services;

import com.prince.myproj.blog.models.ListPageModel;
import com.prince.myproj.shares.dao.SharesHistoryDao;
import com.prince.myproj.shares.dao.SharesTempDao;
import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.models.AnalysisBean;
import com.prince.myproj.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zidong.wang on 2016/5/23.
 */
@Service
public class ShareAnalysisService {

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
            Map<String,Object> paramMap = new HashMap<String, Object>();
            paramMap.put("code",code);
            paramMap.put("day",day);
            List<SharesModel> codeModels = sharesHistoryDao.selectModelByCode(paramMap);
            resultMap.put(code,codeModels);
        }

        return resultMap;
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

}
