package com.prince.myproj.platform.novel.controllers;

import com.prince.myproj.platform.common.models.AjaxModel;
import com.prince.myproj.platform.common.statics.ErrorCode;
import com.prince.myproj.platform.novel.models.NovelModel;
import com.prince.myproj.platform.novel.services.NovelService;
import com.prince.myproj.platform.novel.services.NovelSpiderService;
import com.prince.myproj.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.metadata.AbstractJmxAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by zidong.wang on 2017/2/13.
 */
@Controller
@RequestMapping("/blog/pl/nv")
public class NovelController {

    private static final Logger logger =  Logger.getLogger(NovelController.class);


    @Autowired
    public NovelService novelService;

    @RequestMapping(value = "/getNovelContent")
    @ResponseBody
    public AjaxModel novelContent(HttpServletRequest request){
        long chapter = StringUtil.parseLongFromRequest(request,"chapter",0);
        long novelId = StringUtil.parseLongFromRequest(request,"novelId",-1);
        if(novelId==-1){
            AjaxModel ajaxModel = new AjaxModel();
            ajaxModel.setStatus(ErrorCode.NOT_NOVEL_ID_ERROR);
            return ajaxModel;
        }
        AjaxModel ajaxModel = novelService.spiderNovelByNovelIdAndChapter(novelId, chapter);
        return ajaxModel;
    }


    @RequestMapping(value = "/novelListAll")
    @ResponseBody
    public AjaxModel novelListAll(){
        AjaxModel ajaxModel = novelService.giveMeNovelListAll();
        return ajaxModel;
    }

    @RequestMapping(value = "/novelListPage")
    @ResponseBody
    public AjaxModel novelListPage(HttpServletRequest request){
        int pno = StringUtil.parseIntFromRequest(request,"pno",0);
        int psize = StringUtil.parseIntFromRequest(request, "psize", 10);
        AjaxModel ajaxModel = novelService.giveMeNovelCateListPage(pno, psize, null);
        return ajaxModel;
    }
    @RequestMapping(value = "/novelIndexListPage")
    @ResponseBody
    public AjaxModel novelIndexListPage(HttpServletRequest request){
        int pno = StringUtil.parseIntFromRequest(request, "pno", 0);
        int psize = StringUtil.parseIntFromRequest(request, "psize", 10);
        long novelId = StringUtil.parseLongFromRequest(request, "novelId", 140);
        AjaxModel ajaxModel = novelService.giveMeNovelIndexListPage(novelId, pno, psize);
        return ajaxModel;
    }
    @RequestMapping(value = "/novelRandomBooks")
    @ResponseBody
    public AjaxModel randomBooks(HttpServletRequest request){
        int num = StringUtil.parseIntFromRequest(request, "num", 3);
        AjaxModel ajaxModel = novelService.givemeRandomBooks(num);
        return ajaxModel;
    }

    @RequestMapping(value = "/novelById")
    @ResponseBody
    public AjaxModel novelById(HttpServletRequest request){
        long novelId = StringUtil.parseLongFromRequest(request,"id",-1);
        if(novelId==-1){
            AjaxModel ajaxModel = new AjaxModel();
            ajaxModel.setStatus(ErrorCode.NOT_FIND_ERROR);
            return ajaxModel;
        }
        int needAll = StringUtil.parseIntFromRequest(request,"needAll",0);
        AjaxModel ajaxModel = novelService.giveMeNovelById(novelId, needAll);
        return  ajaxModel;

    }

    @RequestMapping(value = "/recommendPage")
    @ResponseBody
    public AjaxModel recommendPage(HttpServletRequest request){
        AjaxModel ajaxModel = new AjaxModel();

        Map<String,Object> map = new HashMap<String,Object>();

        List<NovelModel> recommendList = novelService.giveMeRecommendList();
        map.put("recommend",recommendList);

        List<NovelModel> recommendBoyList = novelService.giveMeBoyRecommendList(8);
        map.put("boy",recommendBoyList);

        List<NovelModel> recommendGirList = novelService.giveMeGirlRecommendList(8);
        map.put("girl",recommendGirList);

        List<NovelModel> updateList = novelService.updateList(8);
        map.put("update",updateList);

        ajaxModel.setData(map);
        ajaxModel.setStatus(ErrorCode.SUCCESS);
        return ajaxModel;
    }

    @RequestMapping(value = "/novelCateListPage")
    @ResponseBody
    public AjaxModel novelCateListPage(HttpServletRequest request){
        int pno = StringUtil.parseIntFromRequest(request,"pno",0);
        int psize = StringUtil.parseIntFromRequest(request, "psize", 10);
        String cate = StringUtil.parseStringFromRequest(request, "cate", "玄幻小说");
        logger.info("cate:" + cate);
        AjaxModel ajaxModel = novelService.giveMeNovelCateListPage(pno, psize, cate);
        return ajaxModel;
    }

    @RequestMapping(value = "/novelSearchListPage")
    @ResponseBody
    public AjaxModel novelSearchListPage(HttpServletRequest request){
        int pno = StringUtil.parseIntFromRequest(request,"pno",0);
        int psize = StringUtil.parseIntFromRequest(request, "psize", 10);
        String key = StringUtil.parseStringFromRequest(request, "key", "");
        if("".equals(key)){
            AjaxModel ajaxModel = new AjaxModel();
            ajaxModel.setStatus(ErrorCode.NOT_SEARCHKEY_FOUND);
            return ajaxModel;
        }

        AjaxModel ajaxModel = novelService.giveMeSearchResult(pno, psize, key);
        return ajaxModel;
    }

}
