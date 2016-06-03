package com.prince.myproj.shares.services;

import com.prince.myproj.shares.dao.SharesHistoryDao;
import com.prince.myproj.shares.models.SharesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2016/5/23.
 */
@Service
public class ShareAnalysisService {

    @Autowired
    private SharesHistoryDao sharesHistoryDao;

    //�ҳ�����Ⱦ��ߵ͵Ĵ���
    public void findListLowMean(){

    }
    //�ҳ�����Ⱦ��߸ߵĴ���
    public void findListHighMean(){

    }
    //�ҳ������Ƿ��ܴ� �������� �������ǣ�
    public void findListHighIncreasePer(){

    }

    //�ҳ�������ѡ�Ƿ����б�
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

    //��ȡ����ָ����յĹ�ָ
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

}
