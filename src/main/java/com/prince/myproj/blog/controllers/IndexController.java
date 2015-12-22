package com.prince.myproj.blog.controllers;

import com.prince.myproj.blog.models.*;
import com.prince.myproj.blog.services.FriendLinkService;
import com.prince.myproj.blog.services.IndexService;
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
    private IndexService indexService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private FriendLinkService friendLinkService;

    //首页mapping
    @RequestMapping("/index")
    public String viewIndex(HttpServletRequest request,HttpServletResponse response,Model model){
        Map<String,Object> indexResult = new HashMap<String, Object>();

        SuggestModel suggestModel = indexService.getRandomSuggestModel();

        ListPageModel listPage = new ListPageModel();

        List<DailyModel> dailys = preparedDailys(request, listPage);
        filterDailys(dailys);

        MusicModel musicModel = indexService.getRandomMusic();

        List<FriendLinkModel> friendLinkModels = friendLinkService.getSomeFriendLink(20);


        indexResult.put("suggest",suggestModel);
        indexResult.put("listpage", listPage);
        indexResult.put("dailys",dailys);
        indexResult.put("music",musicModel);
        indexResult.put("friendLinks",friendLinkModels);

        model.addAttribute("resultMap", indexResult);
        return "index";
    }

    private List<DailyModel> preparedDailys(HttpServletRequest request,ListPageModel listPage){
        String pnoStr = utilService.getDefaultWhenNull(request.getParameter("pno"), "0");
        int pno = Integer.parseInt(pnoStr);
        String psizeStr = utilService.getDefaultWhenNull(request.getParameter("psize"), "5");
        int psize = Integer.parseInt(psizeStr);

        listPage.setPno(pno);
        listPage.setPsize(psize);
        long allConunt = indexService.getCountByCate("");
        long allPage = (allConunt-1)/psize+1;
        listPage.setAllCount(allConunt);
        listPage.setAllPage(allPage);



//        String cate = utilService.getDefaultWhenNull(request.getParameter("cate"))
        return indexService.getDailyListByPage(pno, psize, "");
    }

    private void filterDailys(List<DailyModel> dailys){
        int size = dailys.size();
        for(int i=0;i<size;i++){
            DailyModel daily = dailys.get(i);
            daily.setCreateTimeStr(utilService.formateDate(daily.getCreateTime()));
            daily.setContent(utilService.replaceTag(daily.getContent()));
            daily.setContent(utilService.replaceBr(utilService.spliceString(daily.getContent(),120,4)));
        }
    }
}
