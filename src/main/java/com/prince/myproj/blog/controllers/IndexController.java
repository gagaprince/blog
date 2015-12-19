package com.prince.myproj.blog.controllers;

import com.prince.myproj.blog.models.SuggestModel;
import com.prince.myproj.blog.services.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gagaprince on 15-12-19.
 */
@Controller
@RequestMapping("/blog")
public class IndexController {
    @Autowired
    private IndexService indexService;

    //首页mapping
    @RequestMapping("/index")
    public String viewIndex(HttpServletRequest request,HttpServletResponse response,Model model){
        Map<String,Object> indexResult = new HashMap<String, Object>();
        SuggestModel suggestModel = indexService.getRandomSuggestModel();
        indexResult.put("suggest",suggestModel);
        model.addAttribute("resultMap",indexResult);
        return "index";
    }
}
