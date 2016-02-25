package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.FontLinkDao;
import com.prince.myproj.blog.models.FontLinkModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2015/12/25.
 */
@Service
public class FontLinkService {

    public static final Logger logger = Logger.getLogger(fontLinkDao.class);

    @Autowired
    private FontLinkDao fontLinkDao;

    public List<FontLinkModel> giveMeUpdateLink(){
        String bigCate = "栏目更新";
        return giveMeModelsByCateAndSize(bigCate,10);
    }
    public List<FontLinkModel> giveMePhotoFontLink(){
        String bigCate = "图文并茂";
        return giveMeModelsByCateAndSize(bigCate,3);
    }
    public List<FontLinkModel> giveMeHotClick(){
        String bigCate = "热门点击";
        return giveMeModelsByCateAndSize(bigCate,10);
    }
    public List<FontLinkModel> giveMePhotoLink(){
        String bigCate = "摄影作品";
        return giveMeModelsByCateAndSize(bigCate,10);
    }


    private List<FontLinkModel> giveMeModelsByCateAndSize(String bigCate,int size){
        Map<String,Object> limitMap = new HashMap<String,Object>();
        limitMap.put("bigCate",bigCate);
        limitMap.put("fromIndex",0);
        limitMap.put("toIndex",size);

        logger.info(bigCate);

        return fontLinkDao.getFontLinksByCate(limitMap);
    }

}
