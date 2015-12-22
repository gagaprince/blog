package com.prince.myproj.blog.controllers;

import com.prince.myproj.blog.models.ListPageModel;
import com.prince.myproj.blog.models.PhotoFolderModel;
import com.prince.myproj.blog.models.PhotoModel;
import com.prince.myproj.blog.services.PhotoService;
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
 * Created by zidong.wang on 2015/12/22.
 */
@Controller
@RequestMapping("/blog/photo/")
public class PhotoCotroller {
    public static final Logger logger = Logger.getLogger(PhotoCotroller.class);

    @Autowired
    private PhotoService photoService;
    @Autowired
    private UtilService utilService;

    @RequestMapping("/folder")
    public String viewToPhotoFolder(HttpServletRequest request,HttpServletResponse response,Model model){
        String pnoStr = utilService.getDefaultWhenNull(request.getParameter("pno"),"0");
        int pno = Integer.parseInt(pnoStr);

        String psizeStr = utilService.getDefaultWhenNull(request.getParameter("psize"), "9");
        int psize = Integer.parseInt(psizeStr);

        List<PhotoFolderModel> photoFolderModels = photoService.giveMePhotoFolders(pno, psize);

        int allCount = photoService.giveMeAllcountPhotoFolder();
        ListPageModel listPageModel = new ListPageModel();
        listPageModel.setPno(pno);
        listPageModel.setPsize(psize);
        listPageModel.setAllCount(allCount);
        listPageModel.setAllPage((allCount - 1) / psize + 1);

        Map<String,Object> indexResult = new HashMap<String, Object>();
        indexResult.put("photoFolders",photoFolderModels);
        indexResult.put("listPage",listPageModel);

        model.addAttribute("resultMap",indexResult);
        return "photo/photoFolder";
    }

    @RequestMapping("/photo")
    public String viewToPhoto(HttpServletRequest request,HttpServletResponse response , Model model){

        Map<String,Object> indexResult = new HashMap<String, Object>();

        String pnoStr = utilService.getDefaultWhenNull(request.getParameter("pno"), "0");
        int pno = Integer.parseInt(pnoStr);

        String psizeStr = utilService.getDefaultWhenNull(request.getParameter("psize"), "9");
        int psize = Integer.parseInt(psizeStr);

        String folderIdStr = utilService.getDefaultWhenNull(request.getParameter("folderId"), "");
        if(!"".equals(folderIdStr)){
            int folderId = Integer.parseInt(folderIdStr);
            List<PhotoModel> photos = photoService.giveMePhotosByFolder(pno,psize,folderId);
            indexResult.put("photos",photos);

            int allCount = photoService.giveAllPhotoByFolder(folderId);

            ListPageModel listPageModel = new ListPageModel();
            listPageModel.setPsize(psize);
            listPageModel.setPno(pno);
            listPageModel.setAllCount(allCount);
            listPageModel.setAllPage((allCount-1)/psize+1);

            indexResult.put("listPage",listPageModel);
        }

        model.addAttribute("resultMap",indexResult);
        return "photo/photo";
    }

}
