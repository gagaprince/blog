package com.prince.myproj.shares.controllers;

import com.alibaba.fastjson.JSON;
import com.prince.myproj.shares.models.WaveModel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by zidong.wang on 2016/12/21.
 */
@Controller
@RequestMapping("/shares/littleWave")
@ResponseBody
public class LittleWaveController {
    public static final Logger logger = Logger.getLogger(LittleWaveController.class);
    @RequestMapping(value="/waveBegin",method = RequestMethod.POST)
    public String waveBegin(@RequestBody WaveModel waveModel){

        logger.info("waveModel"+waveModel.toString());

        return JSON.toJSONString(waveModel);
    }
}
