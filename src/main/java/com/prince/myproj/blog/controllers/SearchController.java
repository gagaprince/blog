package com.prince.myproj.blog.controllers;

import com.alibaba.fastjson.JSON;
import com.prince.myproj.blog.annotations.FooterCommon;
import com.prince.myproj.blog.configs.LuceneConfiger;
import com.prince.myproj.blog.models.BlogIndexModel;
import com.prince.myproj.blog.models.ListPageModel;
import com.prince.myproj.blog.models.ResultModel;
import com.prince.myproj.blog.services.LuceneService;
import com.prince.myproj.blog.services.PageService;
import com.prince.myproj.blog.services.UtilService;
import com.prince.util.fileutil.FileUtil;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gagaprince on 16-1-2.
 */
@Controller
@RequestMapping("/blog")
public class SearchController {

    @Autowired
    public LuceneConfiger luceneConfiger;

    @Autowired
    public LuceneService luceneService;

    @Autowired
    public UtilService utilService;

    @Autowired
    public PageService pageService;

    @RequestMapping("/search")
    @FooterCommon
    public String viewToSearchPage(HttpServletRequest request,HttpServletResponse response,Model model){
        ListPageModel listPageModel = pageService.preparedListPage(request, 6);
        String key = utilService.getDefaultWhenNull(request.getParameter("key"), "");
        List<BlogIndexModel> blogIndexModels = null;
        if(!"".equals(key)){
            try {
                blogIndexModels = luceneService.searchBlog(key,listPageModel);
                filterSearchContent(blogIndexModels);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (blogIndexModels==null){
                    blogIndexModels = new ArrayList<BlogIndexModel>();
                }
            }
        }else{
            blogIndexModels = new ArrayList<BlogIndexModel>();
        }

        Map<String,Object> searchResultMap = new HashMap<String, Object>();
        searchResultMap.put("searchModels",blogIndexModels);
        searchResultMap.put("listpage",listPageModel);
        model.addAttribute("searchResultMap",searchResultMap);
        return "searchPage";
    }

    private void filterSearchContent(List<BlogIndexModel> blogIndexModels){
        int size = blogIndexModels.size();
        for(int i=0;i<size;i++){
            BlogIndexModel blogIndexModel = blogIndexModels.get(i);
            blogIndexModel.setContent(utilService.replaceTag(blogIndexModel.getContent()));
            blogIndexModel.setContent(utilService.replaceBr(utilService.spliceString(blogIndexModel.getContent(),120,4)));
        }

    }

    @RequestMapping("/search/create")
    @ResponseBody
    public String createIndex(HttpServletRequest request,HttpServletResponse response){
        ResultModel resultModel = new ResultModel();
        try {
            String path = luceneConfiger.getIndexPath();

            FileUtil fileUtil = FileUtil.getInstance();

            fileUtil.deleteFile(path);
            fileUtil.createPathFile(path);

            luceneService.createIndex();
            resultModel.getBstatus().setCode(0);
            resultModel.setData("success create index in path : "+path);
        } catch (IOException e) {
            e.printStackTrace();
            resultModel.getBstatus().setCode(-4);
            resultModel.getBstatus().setDesc("建索引失败");
        }
        return JSON.toJSONString(resultModel);

    }
}
