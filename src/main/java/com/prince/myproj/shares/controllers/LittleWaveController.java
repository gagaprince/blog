package com.prince.myproj.shares.controllers;

import com.alibaba.fastjson.JSON;
import com.prince.myproj.shares.models.SingleWaveModel;
import com.prince.myproj.shares.models.WaveModel;
import com.prince.myproj.shares.services.LittleWaveService;
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
 * Created by zidong.wang on 2016/12/21.
 */
@Controller
@RequestMapping("/shares/littleWave")
@ResponseBody
public class LittleWaveController {
    public static final Logger logger = Logger.getLogger(LittleWaveController.class);

    @Autowired
    private LittleWaveService littleWaveService;

    @RequestMapping(value="/waveBegin",method = RequestMethod.POST)
    public String waveBegin(@RequestBody WaveModel waveModel){

        Map<String,List<SingleWaveModel>> singleWaveModelsMap = littleWaveService.beginWave(waveModel);
        return JSON.toJSONString(singleWaveModelsMap);
    }
    @RequestMapping("/testWave")
    public String testWave(HttpServletRequest request){
        String codes = request.getParameter("codes");
        return "";
    }
}
