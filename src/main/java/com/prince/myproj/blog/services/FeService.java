package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.FeDao;
import com.prince.myproj.blog.models.FeModel;
import com.prince.myproj.blog.models.ListPageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2015/12/25.
 */
@Service
public class FeService {
    @Autowired
    private FeDao feDao;

    public List<FeModel> getFeListByPage(int pno,int psize){
        int begin = pno*psize;
        int length = psize;
        Map<String,Object> limitMap = new HashMap<String, Object>();
        limitMap.put("fromIndex",begin);
        limitMap.put("toIndex",length);
        return feDao.getFeList(limitMap);
    }

    public List<FeModel> getFeListByPage(ListPageModel listPageModel){
        int pno = listPageModel.getPno();
        int psize = listPageModel.getPsize();

        long allCount = feDao.getAllCount();
        long allPage = (allCount-1)/psize+1;
        listPageModel.setAllPage(allPage);
        listPageModel.setAllCount(allCount);

        return getFeListByPage(pno, psize);

    }

    public FeModel getFeModelById(long id){
        Map<String,Object> idMap = new HashMap<String, Object>();
        idMap.put("id",id);
        return feDao.getFeById(idMap);
    }
}
