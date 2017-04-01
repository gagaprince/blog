package com.prince.myproj.platform.photo.controllers;

import com.prince.myproj.platform.common.models.AjaxModel;
import com.prince.myproj.platform.photo.services.MMService;
import com.prince.myproj.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zidong.wang on 2017/3/24.
 */
@Controller
@RequestMapping("/blog/pl/mm")
public class PhotoController {
    private static final Logger logger =  Logger.getLogger(PhotoController.class);

    @Autowired
    private MMService mmService;

    @RequestMapping(value = "/getMMPhotos")
    @ResponseBody
    public AjaxModel mmPhotos(HttpServletRequest request){
        int pno = StringUtil.parseIntFromRequest(request,"pno",-1);
        if(pno<0){
            pno=0;
        }
        AjaxModel ajaxModel = mmService.giveMeMMByPno(pno);
        return ajaxModel;
    }
}
