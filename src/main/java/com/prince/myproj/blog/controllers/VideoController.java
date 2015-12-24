package com.prince.myproj.blog.controllers;

import com.prince.myproj.blog.annotations.FooterCommon;
import com.prince.myproj.blog.models.ListPageModel;
import com.prince.myproj.blog.models.VideoModel;
import com.prince.myproj.blog.services.PageService;
import com.prince.myproj.blog.services.UtilService;
import com.prince.myproj.blog.services.VideoService;
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
 * Created by gagaprince on 15-12-24.
 */
@Controller
@RequestMapping("/blog")
public class VideoController {

    @Autowired
    private UtilService utilService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private PageService pageService;

    @RequestMapping("/video")
    @FooterCommon
    public String viewToVideo(HttpServletRequest request,HttpServletResponse response,Model model){
        //导向视频教程
        ListPageModel listPageModel = pageService.preparedListPage(request,9);
        List<VideoModel> videos = videoService.getVideosByPage(listPageModel, "");
        videoService.filterVideos(videos);


        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("videos",videos);
        resultMap.put("listpage",listPageModel);
        model.addAttribute("videoResultMap",resultMap);

        return "video";
    }
    @RequestMapping("/video/detail")
    @FooterCommon
    public String viewToVideoDetail(HttpServletRequest request,HttpServletResponse response,Model model){
        String idStr = utilService.getDefaultWhenNull(request.getParameter("id"),"1");
        long id = Long.parseLong(idStr);
        VideoModel video = videoService.getVideoById(id);

        Map<String,Object> videoResultMap = new HashMap<String, Object>();
        videoResultMap.put("video", video);
        model.addAttribute("videoResultMap",videoResultMap);

        return "videoDetail";
    }
}