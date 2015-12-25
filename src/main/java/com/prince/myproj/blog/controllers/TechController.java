package com.prince.myproj.blog.controllers;

import com.prince.myproj.blog.annotations.FooterCommon;
import com.prince.myproj.blog.models.DailyModel;
import com.prince.myproj.blog.models.ListPageModel;
import com.prince.myproj.blog.services.DailyService;
import com.prince.myproj.blog.services.PageService;
import com.prince.myproj.blog.services.UtilService;
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

        ListPageModel listPageModel = pageService.preparedListPage(request, 7);
        List<DailyModel> dailyModels = dailyService.getDailyListByPage(listPageModel, bigCate);
        dailyService.filterDailys(dailyModels);

        Map<String,Object> techResultMap = new HashMap<String, Object>();
        techResultMap.put("dailys",dailyModels);
        techResultMap.put("listpage",listPageModel);

        model.addAttribute("techResultMap",techResultMap);

        return bigCate;
    }
}
