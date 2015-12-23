package com.prince.myproj.blog.controllers;

import com.prince.myproj.blog.annotations.FooterCommon;
import com.prince.myproj.blog.models.DailyModel;
import com.prince.myproj.blog.models.ListPageModel;
import com.prince.myproj.blog.services.DailyService;
import com.prince.myproj.blog.services.UtilService;
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
 * Created by gagaprince on 15-12-23.
 */
@Controller
@RequestMapping("/blog")
public class TechController {

    @Autowired
    private UtilService utilService;

    @Autowired
    private DailyService dailyService;

    @RequestMapping("/tech")
    @FooterCommon
    public String viewToTech(HttpServletRequest request,HttpServletResponse response ,Model model){
        //导向技术博客
        String bigCate = "tech";
        String pnoStr = utilService.getDefaultWhenNull(request.getParameter("pno"), "0");
        int pno = Integer.parseInt(pnoStr);
        String psizeStr = utilService.getDefaultWhenNull(request.getParameter("psize"), "7");
        int psize = Integer.parseInt(psizeStr);

        List<DailyModel> dailyModels = dailyService.getDailyListByPage(pno, psize, bigCate);
        dailyService.filterDailys(dailyModels);

        ListPageModel listPageModel = preparedListPage(pno,psize,bigCate);

        Map<String,Object> techResultMap = new HashMap<String, Object>();
        techResultMap.put("dailys",dailyModels);
        techResultMap.put("listpage",listPageModel);

        model.addAttribute("techResultMap",techResultMap);

        return "tech";
    }

    private ListPageModel preparedListPage(int pno,int psize,String bigCate){
        ListPageModel listPage = new ListPageModel();
        listPage.setPno(pno);
        listPage.setPsize(psize);
        long allConunt = dailyService.getCountByCate(bigCate);
        long allPage = (allConunt-1)/psize+1;
        listPage.setAllCount(allConunt);
        listPage.setAllPage(allPage);
        return listPage;
    }
}
