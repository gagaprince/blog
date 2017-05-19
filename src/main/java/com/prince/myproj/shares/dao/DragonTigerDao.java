package com.prince.myproj.shares.dao;

import com.prince.myproj.shares.models.DivisionBean;
import com.prince.myproj.shares.models.DragonTigerBean;

import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2017/5/18.
 */
public interface DragonTigerDao {
    public void save(DragonTigerBean model);
    public DragonTigerBean getDragonTigerByCodeAndDate(Map<String, Object> keyMap);
    public List<DragonTigerBean> getDragonTigerByDate(Map<String,String> map);

}

