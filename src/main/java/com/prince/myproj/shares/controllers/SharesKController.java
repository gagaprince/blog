package com.prince.myproj.shares.controllers;

import com.prince.myproj.shares.models.LHBCacularResult;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.myproj.shares.services.DragonTigerService;
import com.prince.myproj.shares.services.SharesKService;
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
 * Created by gagaprince on 2017/5/31.
 */

@Controller
@RequestMapping("/shares")
public class SharesKController {

    @Autowired
    private SharesKService sharesKService;

    @Autowired
    private DragonTigerService dragonTigerService;

    @RequestMapping(value="/findSharesByK",method = RequestMethod.GET)
    @ResponseBody
    public Object findSharesByK(HttpServletRequest request){
        String date = request.getParameter("date");

        List<SharesSingleModel> sharesSingleModels = sharesKService.findSharesByK(date);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("resultList",sharesSingleModels);
        map.put("size",sharesSingleModels.size());

        return map;
    }
    @RequestMapping(value="/cacularSuccessPerByK",method = RequestMethod.GET)
    @ResponseBody
    public Object cacularSuccessPerByK(HttpServletRequest request){
        String date = request.getParameter("date");

        List<SharesSingleModel> sharesSingleModels = sharesKService.findSharesByK(date);

        List<LHBCacularResult> lhbCacularResults = dragonTigerService.validateCaculate(sharesSingleModels, date);



        return lhbCacularResults;

    }
}
