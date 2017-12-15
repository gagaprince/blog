package com.prince.myproj.blog.controllers;

import com.prince.myproj.blog.annotations.FooterCommon;
import com.prince.myproj.blog.models.DailyModel;
import com.prince.myproj.blog.models.ListPageModel;
import com.prince.myproj.blog.services.DailyService;
import com.prince.myproj.blog.services.PageService;
import com.prince.myproj.blog.services.UtilService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gagaprince on 15-12-23.
 */
@Controller
@RequestMapping("/blog")
public class TechController {
    public static final Logger logger = Logger.getLogger(TechController.class);

    @Autowired
    private UtilService utilService;

    @Autowired
    private DailyService dailyService;
    @Autowired
    private PageService pageService;

    @RequestMapping("/cate/{bigCate}")
    @FooterCommon
    public String viewToTech(HttpServletRequest request,HttpServletResponse response ,Model model,@PathVariable String bigCate){
        //导向对应博客

        ListPageModel listPageModel = pageService.preparedListPage(request, 10);
        preparedTech(listPageModel,bigCate,model);
        return bigCate;
    }
    @RequestMapping("/cate/{bigCate}/{pno}")
    @FooterCommon
    public String viewToTech2(HttpServletRequest request,HttpServletResponse response ,Model model,@PathVariable String bigCate,@PathVariable int pno){
        ListPageModel listPageModel = pageService.preparedListPage(pno,10);
        preparedTech(listPageModel,bigCate,model);
        return bigCate;
    }
    @RequestMapping("/cate/{bigCate}/{pno}/{psize}")
    @FooterCommon
    public String viewToTech3(HttpServletRequest request,HttpServletResponse response ,Model model,@PathVariable String bigCate,@PathVariable int pno,@PathVariable int psize){
        ListPageModel listPageModel = pageService.preparedListPage(pno,psize);
        preparedTech(listPageModel,bigCate,model);
        return bigCate;
    }

    private void preparedTech(ListPageModel listPageModel,String bigCate,Model model){
        List<DailyModel> dailyModels = dailyService.getDailyListByPage(listPageModel, bigCate,true);
        dailyService.filterDailys(dailyModels);

        Map<String,Object> techResultMap = new HashMap<String, Object>();
        techResultMap.put("dailys",dailyModels);
        techResultMap.put("listpage",listPageModel);

        model.addAttribute("techResultMap",techResultMap);
    }

    @RequestMapping("/smallCate/{cate}/{pno}")
    @FooterCommon
    public String viewToSmallCate(HttpServletRequest request,HttpServletResponse response ,Model model,@PathVariable String cate,@PathVariable int pno){
        ListPageModel listPageModel = pageService.preparedListPage(pno,10);
        preparedSmallTech(listPageModel,cate,model);
        return "tech";
    }

    private void preparedSmallTech(ListPageModel listPageModel,String cate,Model model){
        logger.info(cate);
        List<DailyModel> dailyModels = dailyService.getDailyListByPage(listPageModel, cate,false);
        dailyService.filterDailys(dailyModels);
        Map<String,Object> techResultMap = new HashMap<String, Object>();
        techResultMap.put("dailys",dailyModels);
        techResultMap.put("listpage",listPageModel);

        model.addAttribute("techResultMap",techResultMap);
    }
}
