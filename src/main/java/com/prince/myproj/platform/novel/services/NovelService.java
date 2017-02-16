package com.prince.myproj.platform.novel.services;

import com.prince.myproj.platform.common.models.AjaxModel;
import com.prince.myproj.platform.common.statics.Constant;
import com.prince.myproj.platform.common.statics.ErrorCode;
import com.prince.myproj.platform.novel.dao.ChapterDao;
import com.prince.myproj.platform.novel.dao.NovelDao;
import com.prince.myproj.platform.novel.models.ChapterModel;
import com.prince.myproj.platform.novel.models.NovelModel;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zidong.wang on 2017/2/13.
 */
@Service
public class NovelService {
    private static final Logger logger =  Logger.getLogger(NovelService.class);

    private HttpUtil httpUtil = HttpUtil.getInstance();

    @Autowired
    private NovelDao novelDao;
    @Autowired
    private ChapterDao chapterDao;

    public AjaxModel spiderNovelByNovelIdAndChapter(long novelId,long chapter){

        AjaxModel ajaxModel = new AjaxModel();

        NovelModel novelModel = getNovelById(novelId);
        ChapterModel chapterModel = getChapterByNovelIdAndChapter(novelId, chapter);

        if(novelModel!=null&&chapterModel!=null){
            String novelSourceUrl = novelModel.getSourceUrl();
            String chapterSourceUrl = chapterModel.getSourceUrl();
            String url = novelSourceUrl+chapterSourceUrl;

            String htmlContent = httpUtil.getContentByUrl(url);

            Document doc = Jsoup.parse(htmlContent);
            Element contentEle = doc.getElementById("content");
            if(contentEle!=null){
                String contentStr = contentEle.html();
                contentStr = contentStr.replace("<br>\n<br>","<br/>");
//            logger.info(contentStr);
                novelModel.setContent(contentStr);
                ajaxModel.setStatus(ErrorCode.SUCCESS);
                ajaxModel.setData(novelModel);
            }else{
                ajaxModel.setStatus(ErrorCode.NETWORK_ERROR);
            }
        }else if(novelModel==null){
            ajaxModel.setStatus(ErrorCode.NOT_NOVEL_ERROR);
        }else{
            ajaxModel.setStatus(ErrorCode.NOT_NOVEL_CHAPTER_ERROR);
        }
        return ajaxModel;
    }

    public NovelModel getNovelById(long id){
        Map<String,Long> map=new HashMap<String, Long>();
        map.put("id",id);
        return novelDao.getNovelById(map);
    }

    public ChapterModel getChapterByNovelIdAndChapter(long novelId,long chapter){
        ChapterModel chapterModel = new ChapterModel();
        chapterModel.setNovelId(novelId);
        chapterModel.setChapter(chapter);
        chapterModel = chapterDao.getChapterByNovelIdAndChapter(chapterModel);
        return chapterModel;
    }



    public AjaxModel giveMeNovelListAll(){
        AjaxModel ajaxModel = new AjaxModel();
        List<NovelModel>novelModels = novelDao.getAllNovels();
        novelListFilterDes(novelModels);
        Map<String,List> map = new HashMap<String, List>();
        map.put("novelList", novelModels);
        ajaxModel.setData(map);
        ajaxModel.setStatus(ErrorCode.SUCCESS);
        return ajaxModel;
    }
    private void novelListFilterDes(List<NovelModel> novelModels){
        for(int i=0;i<novelModels.size();i++){
            NovelModel novelModel = novelModels.get(i);
            novelModel.setDescripe("");
        }
    }

    public AjaxModel giveMeNovelListPage(int pno,int psize){
        AjaxModel ajaxModel = new AjaxModel();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("fromIndex",pno*psize);
        map.put("toIndex",psize);

        List<NovelModel>novelModels = novelDao.getNovelList(map);
        novelListFilterDes(novelModels);
        Map<String,List> returnmap = new HashMap<String, List>();
        returnmap.put("novelList",novelModels);
        ajaxModel.setData(returnmap);
        ajaxModel.setStatus(ErrorCode.SUCCESS);
        return ajaxModel;
    }

}