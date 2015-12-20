package com.prince.myproj.blog.controllers;

import com.prince.myproj.blog.models.DailyModel;
import com.prince.myproj.blog.services.DetailService;
import com.prince.myproj.blog.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gagaprince on 15-12-20.
 */
@Controller
@RequestMapping("/blog")
public class DetailController {

    @Autowired
    private DetailService detailService;
    @Autowired
    private UtilService utilService;

    @RequestMapping("/detail")
    public String viewDetail(HttpServletRequest request,HttpServletResponse response,Model model){
        long id = Long.parseLong(utilService.getDefaultWhenNull(request.getParameter("id"), "1"));
        DailyModel daily = detailService.getDailyById(id);
        daily.setContent(daily.getContent().replaceAll("\n","<br>"));
        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("daily",daily);
        model.addAttribute("resultMap",resultMap);
        return "detail";
    }
}
