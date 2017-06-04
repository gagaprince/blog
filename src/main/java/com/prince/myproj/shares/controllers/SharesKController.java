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
    @RequestMapping(value="/cacularSuccessPerByKAndDays",method = RequestMethod.GET)
    @ResponseBody
    public Object cacularSuccessPer(HttpServletRequest request){
        String date = request.getParameter("date");
        String dayNum = request.getParameter("dayNum");
        List<LHBCacularResult> lhbCacularResults = sharesKService.caculateSuccessPer(date, dayNum);

        float successPer = dragonTigerService.cuculateSuccessPerByLHBResultList(lhbCacularResults);
        List<LHBCacularResult> lhbCacularResultsForSuccess = dragonTigerService.findSuccessFromResults(lhbCacularResults);
        List<LHBCacularResult> lhbCacularResultsForFeild = dragonTigerService.findFeildFromResults(lhbCacularResults);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("successPer",successPer);
        map.put("successResults", lhbCacularResultsForSuccess);
        map.put("feildResults", lhbCacularResultsForFeild);
        return map;
    }
    @RequestMapping(value="/selectSharesByThreeK",method = RequestMethod.GET)
    @ResponseBody
    public Object selectSharesByThreeK(HttpServletRequest request){
        //根据连续三日红k 量增 选股
        String date = request.getParameter("date");

        List<SharesSingleModel> sharesSingleModels = sharesKService.selectSharesByThreeK(date);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("resultList",sharesSingleModels);
        map.put("size",sharesSingleModels.size());

        return map;

    }
    @RequestMapping(value="/cacularSuccessPerFromThreeK",method = RequestMethod.GET)
    @ResponseBody
    public Object cacularSuccessPerFromThreeK(HttpServletRequest request){
        String date = request.getParameter("date");
        List<SharesSingleModel> sharesSingleModels = sharesKService.selectSharesByThreeK(date);
        List<LHBCacularResult> lhbCacularResults = dragonTigerService.validateCaculate(sharesSingleModels, date);
        return lhbCacularResults;
    }
}
