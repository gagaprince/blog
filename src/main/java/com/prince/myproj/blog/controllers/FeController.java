package com.prince.myproj.blog.controllers;

import com.prince.myproj.blog.annotations.FooterCommon;
import com.prince.myproj.blog.models.FeModel;
import com.prince.myproj.blog.models.ListPageModel;
import com.prince.myproj.blog.services.FeService;
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
 * Created by zidong.wang on 2015/12/25.
 */
@Controller
@RequestMapping("/blog")
public class FeController {

    @Autowired
    private UtilService utilService;
    @Autowired
    private PageService pageService;
    @Autowired
    private FeService feService;

    @RequestMapping("/fe")
    @FooterCommon
    public String viewToFe(HttpServletRequest request,HttpServletResponse response, Model model){
        ListPageModel listPageModel = pageService.preparedListPage(request,12);
        preparedFe(listPageModel,model);
        return "feindex";
    }

    @RequestMapping("/fe/detail")
    @FooterCommon
    public String viewToFeDetail(HttpServletRequest request,HttpServletResponse response, Model model){
        String idStr = utilService.getDefaultWhenNull(request.getParameter("id"),"1");
        long id = Long.parseLong(idStr);


        FeModel feModel = feService.getFeModelById(id);

        Map<String,Object> feResultMap = new HashMap<String, Object>();
        feResultMap.put("feModel", feModel);

        model.addAttribute("feResultMap", feResultMap);
        return "feDetail";
    }
    @RequestMapping("/fe/{pno}")
    @FooterCommon
    public String viewToFe2(HttpServletRequest request,HttpServletResponse response, Model model,@PathVariable int pno){
        ListPageModel listPageModel = pageService.preparedListPage(pno, 12);
        preparedFe(listPageModel, model);

        return "feindex";
    }
    @RequestMapping("/fe/{pno}/{psize}")
    @FooterCommon
    public String viewToFe3(HttpServletRequest request,HttpServletResponse response, Model model,@PathVariable int pno,@PathVariable int psize){
        ListPageModel listPageModel = pageService.preparedListPage(pno,psize);
        preparedFe(listPageModel,model);
        return "feindex";
    }


    private void preparedFe(ListPageModel listPageModel,Model model){
        List<FeModel> feModels = feService.getFeListByPage(listPageModel);

        Map<String,Object> feResultMap = new HashMap<String, Object>();
        feResultMap.put("listpage",listPageModel);
        feResultMap.put("feModels", feModels);

        model.addAttribute("feResultMap",feResultMap);
    }
}
