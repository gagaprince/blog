package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.DailyDao;
import com.prince.myproj.blog.models.DailyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gagaprince on 15-12-21.
 */
@Service
public class UEService {
    @Autowired
    private DailyDao dailyDao;

    public void saveOrUpdate(DailyModel dailyModel){
        if (dailyModel.getId()!=null){
            dailyDao.update(dailyModel);
        }else{
            dailyDao.save(dailyModel);
        }
    }

    public DailyModel getDaily(Long id){
        Map<String,Long> idMap = new HashMap<String,Long>();
        idMap.put("id",id);
        return dailyDao.getDailyById(idMap);
    }
}
