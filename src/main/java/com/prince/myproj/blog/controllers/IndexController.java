package com.prince.myproj.blog.controllers;

import com.prince.myproj.blog.annotations.FooterCommon;
import com.prince.myproj.blog.models.*;
//import com.prince.myproj.blog.services.FriendLinkService;
import com.prince.myproj.blog.services.DailyService;
import com.prince.myproj.blog.services.PageService;
import com.prince.myproj.blog.services.SuggestService;
import com.prince.myproj.blog.services.UtilService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gagaprince on 15-12-19.
 */
@Controller
@RequestMapping("/blog")
public class IndexController {

    public static final Logger logger = Logger.getLogger(IndexController.class);

    @Autowired
    private SuggestService suggestService;
    @Autowired
    private DailyService dailyService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private PageService pageService;
//    @Autowired
//    private FriendLinkService friendLinkService;

    //首页mapping
    @RequestMapping("/index")
    @FooterCommon
    public String viewIndex(HttpServletRequest request,HttpServletResponse response,Model model){
        Map<String,Object> indexResult = new HashMap<String, Object>();

        SuggestModel suggestModel = suggestService.getRandomSuggestModel();

        ListPageModel listPage = pageService.preparedListPage(request,5);

        List<DailyModel> dailys = dailyService.getDailyListByPage(listPage, "");
        dailyService.filterDailys(dailys);

        MusicModel musicModel = dailyService.getRandomMusic();


        indexResult.put("suggest", suggestModel);
        indexResult.put("listpage", listPage);
        indexResult.put("dailys", dailys);
        indexResult.put("music", musicModel);

        model.addAttribute("resultMap", indexResult);
        return "index";
    }
}
