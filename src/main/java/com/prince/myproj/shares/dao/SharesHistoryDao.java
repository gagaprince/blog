package com.prince.myproj.shares.dao;

import com.prince.myproj.shares.models.SharesModel;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by zidong.wang on 2016/5/18.
 */
public interface SharesHistoryDao {
    public void save(SharesModel model);
    public List<SharesModel> selectByMap(Map<String,Object> keyMap);
    public List<SharesModel> selectModelByCode(Map<String,Object> keyMap);
    public List<SharesModel> selectWithDate(Map<String,Object> paramMap);
    public SharesModel selectLastModel(Map<String,Object> paramMap);//��ȡ���ݿ��ж�Ӧcode���һ�������
    public SharesModel selectLastMeanModel(Map<String,Object> paramMap);//��ȡ���һ�������means��model
    public List<SharesModel> selectWithHighLow(Map<String,Object> paramMap);//��ȡ�ض��Ƿ���Χ�Ĺ�Ʊ�б�

    public List<SharesModel> selectSharesByCodeAfterDate(Map<String,Object> paramMap);

    public void updateMeans(SharesModel model);
}
