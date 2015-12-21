package com.prince.myproj.blog.dao;

import com.prince.myproj.blog.models.DailyModel;

import java.util.List;
import java.util.Map;

/**
 * Created by gagaprince on 15-12-20.
 */
public interface DailyDao {
    public void save(DailyModel dailyModel);
    public void update(DailyModel dailyModel);
    public List<DailyModel> getDailyList(Map<String,Object> seMap);
    public DailyModel getDailyById(Map<String,Long> idMap);
    public long getAllCount(Map<String,String> cateMap);
}
