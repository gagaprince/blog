package com.prince.myproj.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gagaprince on 15-12-24.
 */
@Controller
@RequestMapping("/blog")
public class VideoController {
    @RequestMapping("/video")
    public String viewToVideo(HttpServletRequest request,HttpServletResponse response,Model model){
        //导向视频教程

        return "video";
    }
}
