package com.prince.myproj.shares.dao;

import com.prince.myproj.shares.models.DivisionBean;

import java.util.Map;

/**
 * Created by zidong.wang on 2017/5/18.
 */
public interface DivisionDao {
    public void save(DivisionBean model);
    public DivisionBean getDivisionByCode(Map<String,Object> keyMap);

}

