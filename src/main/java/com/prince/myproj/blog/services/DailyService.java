package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.DailyDao;
import com.prince.myproj.blog.dao.MusicDao;
import com.prince.myproj.blog.dao.SuggestDao;
import com.prince.myproj.blog.models.*;
import com.prince.myproj.platform.common.models.AjaxModel;
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
public class DailyService {

    @Autowired
    private DailyDao dailyDao;
    @Autowired
    private MusicDao musicDao;
    @Autowired
    private UtilService utilService;




    public List<DailyModel> getDailyListByPage(int pno,int psize,String bigCate,boolean isBig){
        int begin = pno*psize;
        int end = psize;//limit 是 长度
        Map<String,Object> seMap = new HashMap<String, Object>();
        seMap.put("fromIndex",begin);
        seMap.put("toIndex",end);
        if(!"".equals(bigCate)){
            if(isBig){
                seMap.put("bigCate",bigCate);
            }else {
                seMap.put("cate",bigCate);
            }
        }
        List<DailyModel> dailyList = dailyDao.getDailyList(seMap);
        return dailyList;
    }

    public List<DailyModel> getDailyListByPage(ListPageModel listPage,String bigCate,boolean isBig){
        int pno = listPage.getPno();
        int psize = listPage.getPsize();
        List<DailyModel> dailyList = getDailyListByPage(pno, psize, bigCate,isBig);

        long allConunt =getCountByCate(bigCate,isBig);
        long allPage = (allConunt-1)/psize+1;
        listPage.setAllCount(allConunt);
        listPage.setAllPage(allPage);

        return dailyList;
    }

    private long getCountByCate(String cate,boolean isBig){
        Map<String,String> cateMap = new HashMap<String, String>();
        if(!"".equals(cate)){
            if(isBig){
                cateMap.put("bigCate",cate);
            }else {
                cateMap.put("cate",cate);
            }
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

    public void filterDailys(List<DailyModel> dailys){
        int size = dailys.size();
        for(int i=0;i<size;i++){
            DailyModel daily = dailys.get(i);
            daily.setCreateTimeStr(utilService.formateDate(daily.getCreateTime()));
            daily.setContent(utilService.replaceTag(daily.getContent()));
            daily.setContent(utilService.replaceBr(utilService.spliceString(daily.getContent(),120,4)));
        }
    }

    public List<DailyCateModel> getCateList(){
        return dailyDao.getCateList(null);
    }

}
