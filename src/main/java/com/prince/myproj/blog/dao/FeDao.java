package com.prince.myproj.blog.dao;

import com.prince.myproj.blog.models.FeModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2015/12/25.
 */
public interface FeDao {
    public void save(FeModel feModel);
    public List<FeModel> getFeList(Map<String,Object> limitMap);
    public FeModel getFeById(Map<String,Object> idMap);
    public long getAllCount();
}
