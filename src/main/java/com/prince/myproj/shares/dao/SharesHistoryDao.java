package com.prince.myproj.shares.dao;

import com.prince.myproj.shares.models.SharesModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2016/5/18.
 */
public interface SharesHistoryDao {
    public void save(SharesModel model);
    public List<SharesModel> selectByMap(Map<String,Object> keyMap);
    public List<SharesModel> selectWithDate(Map<String,Object> paramMap);
    public SharesModel selectLastModel(Map<String,Object> paramMap);//获取数据库中对应code最后一天的数据
    public SharesModel selectLastMeanModel(Map<String,Object> paramMap);//获取最后一天计算了means的model
    public List<SharesModel> selectWithHighLow(Map<String,Object> paramMap);//获取特定涨幅范围的股票列表


    public void updateMeans(SharesModel model);
}
