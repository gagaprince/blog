package com.prince.myproj.blog.services;

import com.prince.myproj.blog.dao.FontLinkDao;
import com.prince.myproj.blog.models.DailyCateModel;
import com.prince.myproj.blog.models.DailyModel;
import com.prince.myproj.blog.models.FontLinkModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2015/12/25.
 */
@Service
public class FontLinkService {

    public static final Logger logger = Logger.getLogger(FontLinkService.class);

    @Autowired
    private FontLinkDao fontLinkDao;

    @Autowired
    private DailyService dailyService;

    public List<FontLinkModel> giveMeUpdateLink(){
        List<DailyModel> dailyModels = dailyService.getDailyListByPage(0,10,"",true);
        List<FontLinkModel> fontLinkModels = new ArrayList<FontLinkModel>();
        String bigCate = "栏目更新";
        for(int i=0;i<dailyModels.size();i++){
            DailyModel dailyModel = dailyModels.get(i);
            FontLinkModel fontLinkModel = new FontLinkModel();
            fontLinkModel.setBigcate(bigCate);
            fontLinkModel.setTitle(dailyModel.getTitle());
            fontLinkModel.setLink("/blog/detail/"+dailyModel.getId());
            fontLinkModels.add(fontLinkModel);
        }
        return fontLinkModels;
    }
    public List<FontLinkModel> giveMeCateCountList(){
        List<FontLinkModel> fontLinkModels = new ArrayList<FontLinkModel>();
        List<DailyCateModel> dailyCateModels = dailyService.getCateList();
        for(int i=0;i<dailyCateModels.size();i++){
            DailyCateModel dailyCateModel = dailyCateModels.get(i);
            FontLinkModel fontLinkModel = new FontLinkModel();
            fontLinkModel.setTitle(dailyCateModel.getCateName());
            fontLinkModel.setCount(dailyCateModel.getCount());
            fontLinkModel.setLink("/blog/smallCate/"+dailyCateModel.getCateName()+"/0");
            fontLinkModels.add(fontLinkModel);
        }
        return fontLinkModels;
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
