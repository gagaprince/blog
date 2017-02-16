package com.prince.myproj.platform.novel.controllers;

import com.prince.myproj.platform.common.models.AjaxModel;
import com.prince.myproj.platform.common.statics.ErrorCode;
import com.prince.myproj.platform.novel.models.NovelModel;
import com.prince.myproj.platform.novel.services.NovelService;
import com.prince.myproj.platform.novel.services.NovelSpiderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
        String chapterStr = request.getParameter("chapter");
        if(chapterStr==null){
            chapterStr="0";
        }
        String novelIdStr = request.getParameter("novelId");
        if(novelIdStr==null){
            AjaxModel ajaxModel = new AjaxModel();
            ajaxModel.setStatus(ErrorCode.NOT_NOVEL_ID_ERROR);
            return ajaxModel;
        }
        long chapter = Long.parseLong(chapterStr);
        long novelId = Long.parseLong(novelIdStr);
        AjaxModel ajaxModel = novelService.spiderNovelByNovelIdAndChapter(novelId, chapter);
        return ajaxModel;
    }


    @RequestMapping(value = "/novelListAll")
    @ResponseBody
    public AjaxModel novelListAll(){
        AjaxModel ajaxModel = novelService.giveMeNovelListAll();
        return ajaxModel;
    }

}
