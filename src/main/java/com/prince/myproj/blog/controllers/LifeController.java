package com.prince.myproj.blog.controllers;

import com.alibaba.fastjson.JSON;
import com.prince.myproj.blog.annotations.FooterCommon;
import com.prince.myproj.blog.models.*;
import com.prince.myproj.blog.services.PageService;
import com.prince.myproj.blog.services.PhotoService;
import com.prince.myproj.blog.services.UtilService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    public static final Logger logger = Logger.getLogger(LifeController.class);

    @RequestMapping("/life")
    @FooterCommon
    public String viewToLife(HttpServletRequest request,HttpServletResponse response,Model model){

        ListPageModel listPageModel = pageService.preparedListPage(request,18);
        preparedLife(listPageModel,model);

        return "life";
    }
    @RequestMapping("/life/editor")
    @FooterCommon
    public String viewToLifeEditor(HttpServletRequest request,HttpServletResponse response,Model model){

        ListPageModel listPageModel = pageService.preparedListPage(request,18);
        List<PhotoFolderModel> folders = photoService.giveMePhotoFolders(listPageModel);

        PuBuListModel puBuListModel = utilService.splitList(folders,3);

        Map<String,Object> lifeResultMap = new HashMap<String, Object>();
        lifeResultMap.put("listpage",listPageModel);
//        lifeResultMap.put("folders",folders);
        lifeResultMap.put("puBuListModel",puBuListModel);

        model.addAttribute("lifeResultMap", lifeResultMap);

        return "editor/life";
    }

    @RequestMapping("/life/addRank")
    @ResponseBody
    public String addRankForLifePhoto(HttpServletRequest request,HttpServletResponse response){
        String idstr = utilService.getDefaultWhenNull(request.getParameter("id"), "-1");
        long id = Long.parseLong(idstr);
        String rankStr = utilService.getDefaultWhenNull(request.getParameter("rank"), "100");
        int rank = Integer.parseInt(rankStr);
        ResultModel resultModel = new ResultModel();
        if(id==-1){
            resultModel.getBstatus().setCode(-2);
            resultModel.getBstatus().setDesc("id error!");
        }else{
            try{
                PhotoFolderModel photoFolderModel = photoService.getPhotoFolderById(id);
                photoFolderModel.setRank(photoFolderModel.getRank()+rank);
                photoService.updatePhotoFolder(photoFolderModel);
                resultModel.getBstatus().setCode(0);
                resultModel.setData(photoFolderModel);
            }catch (Exception e){
                resultModel.getBstatus().setCode(-3);
                resultModel.getBstatus().setDesc("update error!");
            }
        }

        return JSON.toJSONString(resultModel);
    }

    @RequestMapping("/lifeJson")
    @ResponseBody
    public String lifeJson(HttpServletRequest request,HttpServletResponse response){
        ResultModel resultModel = new ResultModel();
        try{
            ListPageModel listPageModel = pageService.preparedListPage(request, 18);
            List<PhotoFolderModel> folders = photoService.giveMePhotoFolders(listPageModel);
            resultModel.getBstatus().setCode(0);
            resultModel.getBstatus().setDesc("");
            Map<String,Object> dateItemMap=new HashMap<String, Object>();
            dateItemMap.put("folders",folders);
            dateItemMap.put("listpage",listPageModel);
            resultModel.setData(dateItemMap);
        }catch (Exception e){
            resultModel.getBstatus().setCode(-1);
            resultModel.getBstatus().setDesc("获取数据失败");
        }

        String jsonString = JSON.toJSONString(resultModel);

        return jsonString;


    }

    @RequestMapping("/life/detail")
    @FooterCommon
    public String viewToLifeDetail(HttpServletRequest request,HttpServletResponse response,Model model){

        ListPageModel listPageModel = pageService.preparedListPage(request,999);
        String folderIdStr = utilService.getDefaultWhenNull(request.getParameter("id"), "1");
        int folderId = Integer.parseInt(folderIdStr);

        preparedLifeDetail(listPageModel,folderId,model);

        return "lifeDetail";
    }

    @RequestMapping("/life/detail/{folderId}")
    @FooterCommon
    public String viewToLifeDetail2(HttpServletRequest request,HttpServletResponse response,Model model,@PathVariable int folderId){
        ListPageModel listPageModel = pageService.preparedListPage(0,999);

        preparedLifeDetail(listPageModel,folderId,model);

        return "lifeDetail";
    }

    private void preparedLifeDetail(ListPageModel listPageModel,int folderId,Model model){
        List<PhotoModel> photos = photoService.giveMePhotosByFolder(listPageModel, folderId);
        PuBuListModel puBuListModel = utilService.splitList(photos, 3);

        Map<String,Object> photoResultMap = new HashMap<String, Object>();
        photoResultMap.put("listpage",listPageModel);
//        photoResultMap.put("photos",photos);
        photoResultMap.put("puBuListModel",puBuListModel);
        model.addAttribute("photoResultMap", photoResultMap);
    }

    @RequestMapping("/life/{pno}")
    @FooterCommon
    public String viewToLife2(HttpServletRequest request,HttpServletResponse response,Model model,@PathVariable int pno){
        ListPageModel listPageModel = pageService.preparedListPage(pno,18);
        preparedLife(listPageModel, model);
        return "life";
    }
    @RequestMapping("/life/{pno}/{psize}")
    @FooterCommon
    public String viewToLife3(HttpServletRequest request,HttpServletResponse response,Model model,@PathVariable int pno,@PathVariable int psize){
        ListPageModel listPageModel = pageService.preparedListPage(pno,psize);
        preparedLife(listPageModel,model);
        return "life";
    }

    private void preparedLife(ListPageModel listPageModel,Model model){
        List<PhotoFolderModel> folders = photoService.giveMePhotoFolders(listPageModel);

        PuBuListModel puBuListModel = utilService.splitList(folders,3);

        Map<String,Object> lifeResultMap = new HashMap<String, Object>();
        lifeResultMap.put("listpage",listPageModel);
//        lifeResultMap.put("folders",folders);
        lifeResultMap.put("puBuListModel",puBuListModel);

        model.addAttribute("lifeResultMap",lifeResultMap);
    }
}
