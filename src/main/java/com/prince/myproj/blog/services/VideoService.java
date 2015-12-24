package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.VideoDao;
import com.prince.myproj.blog.models.ListPageModel;
import com.prince.myproj.blog.models.VideoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by zidong.wang on 2015/12/24.
 */
@Service
public class VideoService {

    @Autowired
    private VideoDao videoDao;

    public List<VideoModel> getVideosByPage(ListPageModel listPageModel,String cate){
        int pno = listPageModel.getPno();
        int psize = listPageModel.getPsize();

        List<VideoModel> videos = getVideosByPage(pno,psize,cate);

        long allConunt =getCountByCate("");
        long allPage = (allConunt-1)/psize+1;
        listPageModel.setAllCount(allConunt);
        listPageModel.setAllPage(allPage);

        return videos;
    }

    private List<VideoModel> getVideosByPage(int pno,int psize,String cate){
        int begin = pno * psize;
        int length = psize;
        Map<String,Object> limitMap = new HashMap<String, Object>();
        limitMap.put("fromIndex", begin);
        limitMap.put("toIndex", length);
        if(!"".equals(cate)){
            limitMap.put("cate",cate);
        }
        return videoDao.getVideos(limitMap);
    }

    private Long getCountByCate(String cate){
        Map<String,Object> cateMap = new HashMap<String, Object>();
        if(!"".equals(cate)){
            cateMap.put("cate",cate);
        }
        long count = videoDao.getAllCount(cateMap);
        return count;
    }

}
