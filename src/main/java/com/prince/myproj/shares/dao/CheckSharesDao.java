package com.prince.myproj.shares.dao;

import com.prince.myproj.shares.models.CheckShareModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2016/5/17.
 */
public interface CheckSharesDao {
    public void save(CheckShareModel model);
    public void update(CheckShareModel model);
    public List<CheckShareModel> getCheckShares(Map<String, Object> seMap);

}
