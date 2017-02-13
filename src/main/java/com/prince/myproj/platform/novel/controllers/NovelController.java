package com.prince.myproj.platform.novel.controllers;

import com.prince.myproj.platform.common.models.AjaxModel;
import com.prince.myproj.platform.common.statics.ErrorCode;
import com.prince.myproj.platform.novel.models.NovelModel;
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
    public NovelSpiderService novelSpiderService;

    @RequestMapping(value = "/getNovelContent")
    @ResponseBody
    public String novelContent(HttpServletRequest request){
        String pno = request.getParameter("pno");
        logger.info(pno);
        if(pno==null){
            pno="0";
        }
        AjaxModel ajaxModel = novelSpiderService.spiderNovelByPno(pno);
        return ajaxModel.toString();
    }

}
