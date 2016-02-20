package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.DailyDao;
import com.prince.myproj.blog.models.DailyModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<DailyModel> getRelativeDailys(String cate,long currentId){
        Map<String,Object> cateMap = new HashMap<String, Object>();
        cateMap.put("cate",cate);
        cateMap.put("fromIndex",0);
        cateMap.put("toIndex",6);
        List<DailyModel> dailys = dailyDao.getSimpleDailyList(cateMap);
        filterDailys(dailys, currentId);
        if(dailys.size()>0){
            return dailys;
        }
        return null;
    }

    private void filterDailys(List<DailyModel> dailys,long id){
        int size = dailys.size();
        for(int i=0;i<size;i++){
            DailyModel daily = dailys.get(i);
            long idIn = daily.getId();
            if(id==idIn){
                dailys.remove(i);
                break;
            }
        }
    }

    public List<String> analysisTags(DailyModel dailyModel){
        List<String> tags = new ArrayList<String>();
        String tagAll = dailyModel.getTag();
        String[] tagsplits = tagAll.split(",");
        if(tagsplits.length>0){
            for (int i=0;i<tagsplits.length&&i<3;i++){
                tags.add(tagsplits[i]);
            }
        }
        return tags;
    }
}
