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
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zidong.wang on 2017/2/13.
 */
@Service
public class NovelSpiderService {
    private static final Logger logger =  Logger.getLogger(NovelSpiderService.class);

    private HttpUtil httpUtil = HttpUtil.getInstance();

    @Autowired
    private NovelDao novelDao;
    @Autowired
    private ChapterDao chapterDao;

    public AjaxModel spiderAll(){
        AjaxModel ajaxModel = new AjaxModel();

        List<NovelModel> allNovels = spiderAllNovels();

        //循环收集完整信息
        for(int i=0;i<allNovels.size();i++){
            NovelModel novelModel = allNovels.get(i);
            spiderOneNovel(novelModel);
        }


        return ajaxModel;
    }
    private List<NovelModel> spiderAllNovels(){
        List<NovelModel> novelModels = new ArrayList<NovelModel>();

        String url = Constant.zw37AllUrl;
        String htmlContent = httpUtil.getContentByUrl(url);
//        logger.info(htmlContent);

        Document doc = Jsoup.parse(htmlContent);
        Elements novelListEles = doc.getElementsByClass("novellist");
        for(int j=0;j<novelListEles.size();j++){
            Element novelListEle = novelListEles.get(j);
            Elements novelAEles = novelListEle.getElementsByTag("a");

            for(int i=0;i<novelAEles.size();i++){
                Element novelA = novelAEles.get(i);
                String name = novelA.html();
                String sourceUrl = novelA.attr("href");

                NovelModel novelModel = new NovelModel();
                novelModel.setName(name);
                novelModel.setSourceUrl(sourceUrl);
                novelModels.add(novelModel);

            }
        }


        return novelModels;
    }

    private void spiderOneNovel(NovelModel novelModel){
        String soureUrl = novelModel.getSourceUrl();
        String name = novelModel.getName();
        logger.info("完整单本信息开始：name:" + name + " link:" + soureUrl);

        String htmlContent = httpUtil.getContentByUrl(soureUrl);
//        logger.info(htmlContent);

        Document doc = Jsoup.parse(htmlContent);

        //先判断库里有没有
        if(novelDao.getNovelBySourceUrl(novelModel)==null){
            spiderOneNovelInfo(novelModel, doc);
            spiderOneNovelChapters(novelModel,doc);

            //持久化~
            try {
                saveNovelAndChapter(novelModel);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void saveNovelAndChapter(NovelModel novelModel){
        Date now = new Date();
        if(novelModel.getCreateTime()==null){
            novelModel.setCreateTime(now);
        }
        novelModel.setUpdateTime(now);
        novelDao.save(novelModel);

        //拿出chapters 插入chapters
        List<ChapterModel> chapterModels = novelModel.getChapters();
        long novelId = novelModel.getId();
        for(int i=0;i<chapterModels.size();i++){
            ChapterModel chapterModel = chapterModels.get(i);
            chapterModel.setNovelId(novelId);
            chapterModel.setCreateTime(new Date());
            chapterDao.save(chapterModel);
        }
        novelModel.setChapters(null);
    }

    private void spiderOneNovelInfo(NovelModel novelModel,Document doc){
        Elements metas = doc.getElementsByTag("meta");
        for(int i=0;i<metas.size();i++){
            Element meta = metas.get(i);
            String property = meta.attr("property");
            if(property.startsWith("og")){
                String content = meta.attr("content");
                if(property.contains("cate")){
                    novelModel.setCate(content);
                }else if(property.contains("author")&&!property.contains("author_")){
                    novelModel.setAuthor(content);
                }else if(property.contains("book_name")){
                    novelModel.setName(content);
                }else if(property.contains("desc")){
                    novelModel.setDescripe(content);
                }
            }
        }
    }

    private void spiderOneNovelChapters(NovelModel novelModel,Document doc){
        List<ChapterModel> chapterModels = parseChaptersByDoc(doc);
        novelModel.setChapters(chapterModels);
    }


    private List<ChapterModel> parseChaptersByDoc(Document doc){
        Element list = doc.getElementById("list");
        Elements chaptersEles = list.getElementsByTag("a");
        List<ChapterModel> chapterModels = new ArrayList<ChapterModel>();

        for(int i=0;i<chaptersEles.size();i++){
            Element chapterEle = chaptersEles.get(i);
            ChapterModel chapterModel = new ChapterModel();
            String sourceUrl = chapterEle.attr("href");
            String name = chapterEle.html();

//            logger.info("章节名："+name+" link:"+sourceUrl);

            chapterModel.setName(name);
            chapterModel.setSourceUrl(sourceUrl);
            chapterModel.setChapter(i);
            chapterModels.add(chapterModel);
        }
        return chapterModels;
    }

    //更新
    public AjaxModel spiderUpdate(){
        AjaxModel ajaxModel = new AjaxModel();

        List<NovelModel> allNovels = novelDao.getAllNovels();
        for(int i=0;i<allNovels.size();i++){
            NovelModel novelModel = allNovels.get(i);
            updateOneNovel(novelModel);
        }
        ajaxModel.setStatus(ErrorCode.SUCCESS);
        return ajaxModel;
    }

    private void updateOneNovel(NovelModel novelModel){
        String sourceUrl = novelModel.getSourceUrl();
        String htmlContent = httpUtil.getContentByUrl(sourceUrl);
        Document doc = Jsoup.parse(htmlContent);
        List<ChapterModel> chapterModels = parseChaptersByDoc(doc);

        //拿出库中的章节 做diff
        ChapterModel lastestChapter = getLastestChapter(novelModel);
        int beginSize;
        if(lastestChapter!=null){
            beginSize = (int)lastestChapter.getChapter()+1;
        }else{
            beginSize=0;
        }
        long novelId = novelModel.getId();
        String novelName = novelModel.getName();
        for(int i=beginSize;i<chapterModels.size();i++){
            ChapterModel chapterModel = chapterModels.get(i);
            logger.info("更新小说："+novelName+": 更新章节："+chapterModel.getName()+":"+chapterModel.getChapter());
            chapterModel.setNovelId(novelId);
            chapterModel.setCreateTime(new Date());
            chapterDao.save(chapterModel);
        }
    }

    private ChapterModel getLastestChapter(NovelModel novelModel){
        Map<String,Long> params = new HashMap<String,Long>();
        params.put("novelId",novelModel.getId());
        params.put("fromIndex",0L);
        params.put("toIndex",1L);

        List<ChapterModel> chapterModelsInDB = chapterDao.getChapterListByNovelId(params);
        if (chapterModelsInDB.size()>0){
            return chapterModelsInDB.get(0);
        }
        return null;
    }
}