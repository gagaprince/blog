package com.prince.myproj.shares.controllers;

import com.alibaba.fastjson.JSON;
import com.prince.myproj.shares.models.WaveModel;
import com.prince.myproj.shares.services.DragonTigerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2017/5/18.
 */
@Controller
@RequestMapping("/shares/DragonTiger")
@ResponseBody
public class DragonTigerController {
    public static final Logger logger = Logger.getLogger(DragonTigerController.class);

    @Autowired
    private DragonTigerService dragonTigerService;

    @RequestMapping(value="/spiderDivision",method = RequestMethod.GET)
    @ResponseBody
    public String spiderDivision(HttpServletRequest request){
        dragonTigerService.spiderDivisionTask();
        return "soiderDivision ok!";
    }
}
