package com.prince.myproj.blog.controllers;

import com.baidu.ueditor.ActionEnter;
import com.prince.myproj.blog.models.DailyModel;
import com.prince.myproj.blog.services.UEService;
import com.prince.myproj.blog.services.UtilService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by gagaprince on 15-12-21.
 */
@Controller
@RequestMapping("/blog/editor")
public class UEditorController {
    public static final Logger logger = Logger.getLogger(UEditorController.class);

    @Autowired
    private UtilService utilService;
    @Autowired
    private UEService ueService;

    @RequestMapping("/index")
    public String viewToEditor(HttpServletRequest request,HttpServletResponse response,Model model){
        String id = request.getParameter("id");
        if (id!=null){
//            model.addAttribute("id",id);
            DailyModel dailyModel = ueService.getDaily(Long.parseLong(id));
            model.addAttribute("daily",dailyModel);
        }
        return "/editor/index";
    }
    @RequestMapping("/ctrl")
    @ResponseBody
    public String viewToCtrl(HttpServletRequest request,HttpServletResponse response,Model model){
        ServletContext application = request.getSession().getServletContext();
        String rootPath = application.getRealPath("/");
        return new ActionEnter( request, rootPath ).exec();
    }
    @RequestMapping("addDaily")
    @ResponseBody
    public String addDaily(HttpServletRequest request,HttpServletResponse response){
        String idStr = request.getParameter("id");
        String title = utilService.getDefaultWhenNull(request.getParameter("title"),"test");
        String cate = utilService.getDefaultWhenNull(request.getParameter("cate"),"test");
        String bigCate = utilService.getDefaultWhenNull(request.getParameter("bigCate"),"test");
        String content = utilService.getDefaultWhenNull(request.getParameter("allContent"),"");
        DailyModel dailyModel = new DailyModel();
        if(idStr!=null){
            dailyModel = ueService.getDaily(Long.parseLong(idStr));
        }else{
            dailyModel.setCreateTime(new Date());
        }
        dailyModel.setTitle(title);
        dailyModel.setCate(cate);
        dailyModel.setBigCate(bigCate);
        dailyModel.setContent(content);

        try {
            ueService.saveOrUpdate(dailyModel);
        }catch (Exception e){
            e.printStackTrace();
            return "{\"code\":1}";
        }
        return "{\"code\":0,\"id\":"+dailyModel.getId()+"}";
    }
}
