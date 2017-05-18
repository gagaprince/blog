package com.prince.myproj.shares.dao;

import com.prince.myproj.shares.models.DivisionBean;
import com.prince.myproj.shares.models.DragonTigerBean;

import java.util.Map;

/**
 * Created by zidong.wang on 2017/5/18.
 */
public interface DragonTigerDao {
    public void save(DragonTigerBean model);
    public DragonTigerBean getDivisionByCodeAndDate(Map<String, Object> keyMap);

}

