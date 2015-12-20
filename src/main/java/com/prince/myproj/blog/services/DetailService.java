package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.DailyDao;
import com.prince.myproj.blog.models.DailyModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gagaprince on 15-12-20.
 */
@Service
public class DetailService {
    public static final Logger logger = Logger.getLogger(DetailService.class);
    @Autowired
    private DailyDao dailyDao;

    public DailyModel getDailyById(long dailyId){
        Map<String,Long> idMap = new HashMap<String, Long>();
        idMap.put("id",dailyId);
        return dailyDao.getDailyById(idMap);
    }
}
