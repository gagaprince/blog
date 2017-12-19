package com.prince.myproj.platform.av.controllers;

import com.prince.myproj.platform.av.services.AVService;
import com.prince.myproj.platform.common.models.AjaxModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/blog/pl/av")
public class AVController {
    private static final Logger logger =  Logger.getLogger(AVController.class);

    @Autowired
    private AVService avService;

    @RequestMapping(value = "/spiderAv")
    @ResponseBody
    public AjaxModel spiderAv(HttpServletRequest request){

        AjaxModel ajaxModel = avService.spiderAv();
        return ajaxModel;
    }

    @RequestMapping(value = "/spiderAvActors")
    @ResponseBody
    public AjaxModel spiderAvActors(HttpServletRequest request){

        AjaxModel ajaxModel = avService.spiderAvActors();
        return ajaxModel;
    }

    @RequestMapping(value = "/spiderAvMoives")
    @ResponseBody
    public AjaxModel spiderAvMoives(HttpServletRequest request){
        AjaxModel ajaxModel = avService.spiderAvMoives();
        return ajaxModel;
    }
}
