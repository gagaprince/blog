package com.prince.myproj.platform.av.services;

import com.prince.myproj.platform.av.dao.AvActorModelMapper;
import com.prince.myproj.platform.av.models.AvActorModel;
import com.prince.myproj.platform.common.models.AjaxModel;
import com.prince.myproj.platform.common.statics.ErrorCode;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AVService {
    private static final Logger logger =  Logger.getLogger(AVService.class);

    private HttpUtil httpUtil = HttpUtil.getInstance();

    @Autowired
    private AvActorModelMapper avActorModelMapper;

    public AjaxModel spiderAv (){
        AjaxModel ajaxModel = new AjaxModel();
        ajaxModel.setStatus(ErrorCode.SUCCESS);

        List<AvActorModel> avActorModels = spiderAvActorList();
        logger.info(avActorModels.size());

        return ajaxModel;
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
