package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.DailyDao;
import com.prince.myproj.blog.dao.MusicDao;
import com.prince.myproj.blog.dao.SuggestDao;
import com.prince.myproj.blog.models.DailyModel;
import com.prince.myproj.blog.models.MusicModel;
import com.prince.myproj.blog.models.SuggestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by gagaprince on 15-12-19.
 */
@Service
public class IndexService {
    @Autowired
    private SuggestDao suggestDao;
    @Autowired
    private DailyDao dailyDao;
    @Autowired
    private MusicDao musicDao;


    public SuggestModel getRandomSuggestModel(){
        List<SuggestModel> suggestModels = suggestDao.getAllSuggest();
        int size = suggestModels.size();
        if (size>0){
            Random r = new Random();
            int index = r.nextInt(size);
            return suggestModels.get(index);
        }
        return null;
    }

    public List<DailyModel> getDailyListByPage(int pno,int psize,String cate){
        int begin = pno*psize;
        int end = psize;//limit 是 长度
        Map<String,Object> seMap = new HashMap<String, Object>();
        seMap.put("fromIndex",begin);
        seMap.put("toIndex",end);
        if(!"".equals(cate)){
            seMap.put("cate",cate);
        }
        List<DailyModel> dailyList = dailyDao.getDailyList(seMap);
        return dailyList;
    }

    public long getCountByCate(String cate){
        Map<String,String> cateMap = new HashMap<String, String>();
        if(!"".equals(cate)){
            cateMap.put("cate",cate);
        }
        long count = dailyDao.getAllCount(cateMap);
        return count;
    }

    public MusicModel getRandomMusic(){
        List<MusicModel> musicModels = musicDao.getMusicList();
        int size = musicModels.size();
        if(size>0){
            Random r = new Random();
            int index = r.nextInt(size);
            return musicModels.get(index);
        }
        return null;

    }

}
