package com.prince.myproj.shares.controllers;

import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.myproj.shares.services.RealTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gagaprince on 2017/6/20.
 */
@Controller
@RequestMapping("/shares")
public class SharesIncreaseOverController {

    @Autowired
    private RealTimeService realTimeService;

    @RequestMapping(value="/realTimeCheck")
    @ResponseBody
    public Object findSharesByK(HttpServletRequest request){
        Map<String,List<Map<String,Object>>> realTimeSharesMap = realTimeService.getRealTimeSharesData(null);

        return realTimeSharesMap;
    }

}
