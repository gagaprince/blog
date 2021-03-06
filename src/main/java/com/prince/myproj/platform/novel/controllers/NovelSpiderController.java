package com.prince.myproj.platform.novel.controllers;

import com.prince.myproj.platform.common.models.AjaxModel;
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
public class NovelSpiderController {

    private static final Logger logger =  Logger.getLogger(NovelSpiderController.class);

    @Autowired
    private NovelSpiderService novelSpiderService;


    //  /blog/pl/nv/spiderAll
    @RequestMapping(value = "/spiderAll")
    @ResponseBody
    public AjaxModel spiderAll(HttpServletRequest request){
        AjaxModel ajaxModel = novelSpiderService.spiderAll();
        return ajaxModel;
    }

    //  /blog/pl/nv/spiderUpdate   type=0 正常更新  1 更新图片  2 只抓新书
    @RequestMapping(value = "/spiderUpdate")
    @ResponseBody
    public AjaxModel spiderUpdate(HttpServletRequest request){
        String type = request.getParameter("type");
        if(type==null){
            type="0";
        }
        AjaxModel ajaxModel = novelSpiderService.spiderUpdate(type);
        return ajaxModel;
    }

}
