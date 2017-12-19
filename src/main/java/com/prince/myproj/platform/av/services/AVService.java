package com.prince.myproj.platform.av.services;

import com.prince.myproj.platform.av.dao.*;
import com.prince.myproj.platform.av.models.*;
import com.prince.myproj.platform.common.models.AjaxModel;
import com.prince.myproj.platform.common.statics.ErrorCode;
import com.prince.myproj.util.DateUtil;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AVService {
    private static final Logger logger =  Logger.getLogger(AVService.class);

    private HttpUtil httpUtil = HttpUtil.getInstance();
    private DateUtil dateUtil = new DateUtil();

    @Autowired
    private AvActorModelMapper avActorModelMapper;

    @Autowired
    private AvMoiveModelMapper avMoiveModelMapper;

    @Autowired
    private AvCateCodeModelMapper avCateCodeModelMapper;

    @Autowired
    private AvCateModelMapper avCateModelMapper;

    @Autowired
    private AvActorCodeModelMapper avActorCodeModelMapper;

    public AjaxModel spiderAv (){
        AjaxModel ajaxModel = new AjaxModel();
        ajaxModel.setStatus(ErrorCode.SUCCESS);

        List<AvActorModel> avActorModels = spiderAvActorList();
        logger.info(avActorModels.size());

        return ajaxModel;
    }

    public AjaxModel spiderAvMoives(){
        AjaxModel ajaxModel = new AjaxModel();
        ajaxModel.setStatus(ErrorCode.SUCCESS);
        spiderAvMoiveList();
        return ajaxModel;
    }

    private void spiderAvMoiveList(){
        List<AvActorModel> avActorModels = avActorModelMapper.selectAll(null);
        logger.info(avActorModels.size());
        for(int i=0;i<avActorModels.size();i++){
            AvActorModel avActorModel = avActorModels.get(i);
            spiderMoivesByActor(avActorModel);
        }
    }

    private void spiderMoivesByActor(AvActorModel avActorModel){
        String host = "http://ja14b.com";
        String link = avActorModel.getSourceUrl();
//        link = "http://ja14b.com/cn/vl_star.php?s=bqpq";
        while(true) {
            logger.info(link);
            String content = httpUtil.getContentByUrl(link);
//            logger.info(content);
            Document doc = Jsoup.parse(content);
            Elements eles = doc.getElementsByClass("video");
            for (int i = 0; i < eles.size(); i++) {
                Element ele = eles.get(i);
                Element a = ele.getElementsByTag("a").first();
                Element img = ele.getElementsByTag("img").first();
                String smallImg = img.attr("src");
                logger.info(img.attr("src"));
                String detailLink = host+"/cn/"+a.attr("href");
//                detailLink = "http://ja14b.com/cn/?v=javlikljqy";
                try {
                    spiderMoive(detailLink, smallImg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            Elements nexts = doc.getElementsByClass("next");
            if(nexts.size()>0){
                Element next = nexts.first();
                link = host + next.attr("href");
            }else{
                break;
            }
        }
    }

    private AvMoiveModel spiderMoive(String link , String smallImg){
        String content = httpUtil.getContentByUrl(link);
        logger.info(content);

        Document doc = Jsoup.parse(content);

        String code = getElementText(doc, "video_id");
        logger.info(code);
        List<AvMoiveModel> avMoiveModels = avMoiveModelMapper.selectByCode(code);
        if(avMoiveModels.size()>0){
            return avMoiveModels.get(0);
        }
        AvMoiveModel avMoiveModel = null;

        Element titleEle = doc.getElementsByClass("post-title").first().getElementsByTag("a").first();
        String title = titleEle.html();
        logger.info(title);

        String date = getElementText(doc,"video_date");
        logger.info(date);

        String length = getElementText(doc, "video_length");
        logger.info(length);

        String director = getElementText(doc, "video_director");
        logger.info("director");
        logger.info(director);

        String maker = getElementText(doc, "video_maker");
        logger.info(maker);

        String runner = getElementText(doc, "video_label");
        logger.info(runner);

        String bigImg = doc.getElementById("video_jacket_img").attr("src");
        logger.info(bigImg);

        avMoiveModel = new AvMoiveModel();
        avMoiveModel.setTitle(title);
        avMoiveModel.setBigImg(bigImg);
        avMoiveModel.setCode(code);
        avMoiveModel.setCreateTime(new Date());
        avMoiveModel.setDirector(director);
        avMoiveModel.setLength(Integer.parseInt(length));
        avMoiveModel.setMaker(maker);
        avMoiveModel.setRunner(runner);
        avMoiveModel.setSmallImg(smallImg);
        avMoiveModel.setSourceUrl(link);
        avMoiveModel.setRunDate(dateUtil.parseDate(date, "yyyy-MM-dd"));


        avMoiveModelMapper.insertSelective(avMoiveModel);

        // 操作分类和演员
        parseCateAndActor(avMoiveModel,doc);

        return avMoiveModel;
    }

    private void parseCateAndActor(AvMoiveModel avMoiveModel,Document doc){
        String host = "http://ja14b.com";
        Elements tags = doc.getElementsByClass("genre");
        for(int i=0;i<tags.size();i++){
            Element tag = tags.get(i).getElementsByTag("a").first();
            String cateName = tag.html();
            String sourceUrl = host+"/cn/"+tag.attr("href");
            List<AvCateModel> avCateModels = avCateModelMapper.selectByCate(cateName);
            AvCateModel avCateModel=null;
            if(avCateModels.size()==0){
                avCateModel = new AvCateModel();
                avCateModel.setCate(cateName);
                avCateModel.setSourceUrl(sourceUrl);
                avCateModelMapper.insertSelective(avCateModel);
            }else{
                avCateModel = avCateModels.get(0);
            }
            logger.info(avCateModel);
            AvCateCodeModel avCateCodeModel = new AvCateCodeModel();
            avCateCodeModel.setCateId((long)avCateModel.getId());
            avCateCodeModel.setCode(avMoiveModel.getCode());
            avCateCodeModelMapper.insertSelective(avCateCodeModel);
        }

        Elements stars = doc.getElementsByClass("cast");
        for(int i=0;i<stars.size();i++){
            Element star = stars.get(i).getElementsByTag("a").first();
            String name = star.html();
            logger.info("演员：");
            logger.info(name);
            String sourceUrl = star.attr("href");
            List<AvActorModel> avActorModels = avActorModelMapper.selectByName(name);
            AvActorModel avActorModel = null;
            if(avActorModels.size()==0){
                avActorModel = new AvActorModel();
                avActorModel.setActor(name);
                avActorModel.setSourceUrl(sourceUrl);
                avActorModelMapper.insertSelective(avActorModel);
            }else {
                avActorModel = avActorModels.get(0);
            }

            AvActorCodeModel avActorCodeModel = new AvActorCodeModel();
            avActorCodeModel.setActorId(avActorModel.getId());
            avActorCodeModel.setCode(avMoiveModel.getCode());
            avActorCodeModelMapper.insertSelective(avActorCodeModel);
        }

    }

    private String getElementText(Document doc ,String id){
        Element codeEle = doc.getElementById(id).getElementsByClass("text").first();
        Element a = codeEle.getElementsByTag("a").first();
        if(a!=null){
            return a.html();
        }
        String code = codeEle.html();
        return code;
    }

    private String getElementTag(Document doc ,String id){
        Element codeEle = doc.getElementById(id).getElementsByClass("text").first();
        Element a = codeEle.getElementsByTag("a").first();
        return a.html();
    }

    public AjaxModel spiderAvActors(){
        AjaxModel ajaxModel = new AjaxModel();
        ajaxModel.setStatus(ErrorCode.SUCCESS);
        List<AvActorModel> avActorModels = spiderAvActorList();
        logger.info(avActorModels.size());
        return ajaxModel;
    }

    private List<AvActorModel> spiderAvActorList(){
        List<AvActorModel> avActorModels = new ArrayList<AvActorModel>();

        String host = "http://ja14b.com";
        char char1 = 'a';
        while(char1<='z'){
            String url = host + "/cn/star_list.php?prefix="+char1;
            while(true){
                String content = httpUtil.getContentByUrl(url);
                logger.info(content);
                List<AvActorModel> AvActorModelsInContent = parseAVActor(content);
                avActorModels.addAll(AvActorModelsInContent);

                Document doc = Jsoup.parse(content);
                Elements nexts = doc.getElementsByClass("next");
                if(nexts.size()>0){
                    Element next = nexts.first();
                    url = host + next.attr("href");
                    logger.info(url);
                }else{
                    break;
                }
            }
            char1 ++;
        }
        return avActorModels;
    }

    private List<AvActorModel> parseAVActor(String content){
        String host = "http://ja14b.com";
        Document doc = Jsoup.parse(content);
        Elements eles = doc.getElementsByClass("searchitem");
        List<AvActorModel> avActorModels = new ArrayList<AvActorModel>();
        for(int i=0;i<eles.size();i++){
            Element ele = eles.get(i);
            Element a = ele.getElementsByTag("a").get(0);
            String actor = a.html();
            String link = host+"/cn/"+a.attr("href");
            AvActorModel avActorModel = new AvActorModel();
            avActorModel.setActor(actor);
            avActorModel.setSourceUrl(link);
            avActorModels.add(avActorModel);
            logger.info(avActorModel.getActor()+":"+avActorModel.getSourceUrl());
            avActorModelMapper.insertSelective(avActorModel);
        }
        return avActorModels;
    }
}
