package com.prince.myproj.shares.dao;

import com.prince.myproj.shares.models.SharesSingleModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2016/5/17.
 */
public interface SharesDao {
    public void save(SharesSingleModel model);
    public List<SharesSingleModel> getShares(Map<String,Object> seMap);
    public List<SharesSingleModel> getSharesIncodes(Map<String,Object> seMap);
    public long getAllCount();
}
