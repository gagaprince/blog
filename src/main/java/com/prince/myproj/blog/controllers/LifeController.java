package com.prince.myproj.blog.controllers;

import com.prince.myproj.blog.annotations.FooterCommon;
import com.prince.myproj.blog.models.ListPageModel;
import com.prince.myproj.blog.models.PhotoFolderModel;
import com.prince.myproj.blog.models.PhotoModel;
import com.prince.myproj.blog.services.PageService;
import com.prince.myproj.blog.services.PhotoService;
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
 * Created by gagaprince on 15-12-24.
 */
@Controller
@RequestMapping("/blog")
public class LifeController {

    @Autowired
    private PageService pageService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private UtilService utilService;

    @RequestMapping("/life")
    @FooterCommon
    public String viewToLife(HttpServletRequest request,HttpServletResponse response,Model model){

        ListPageModel listPageModel = pageService.preparedListPage(request,9);
        List<PhotoFolderModel> folders = photoService.giveMePhotoFolders(listPageModel);

        Map<String,Object> lifeResultMap = new HashMap<String, Object>();
        lifeResultMap.put("listpage",listPageModel);
        lifeResultMap.put("folders",folders);

        model.addAttribute("lifeResultMap",lifeResultMap);

        return "life";
    }
    @RequestMapping("/life/detail")
    @FooterCommon
    public String viewToLifeDetail(HttpServletRequest request,HttpServletResponse response,Model model){

        ListPageModel listPageModel = pageService.preparedListPage(request,12);
        String folderIdStr = utilService.getDefaultWhenNull(request.getParameter("id"), "1");
        int folderId = Integer.parseInt(folderIdStr);

        List<PhotoModel> photos = photoService.giveMePhotosByFolder(listPageModel, folderId);

        Map<String,Object> photoResultMap = new HashMap<String, Object>();
        photoResultMap.put("listpage",listPageModel);
        photoResultMap.put("photos",photos);
        model.addAttribute("photoResultMap", photoResultMap);

        return "lifeDetail";
    }
}
