package com.prince.myproj.blog.services;

import com.prince.myproj.blog.models.DailyModel;
import com.prince.myproj.platform.common.models.AjaxModel;
import com.prince.myproj.platform.common.statics.ErrorCode;
import com.prince.util.httputil.HttpUtil;
import com.prince.util.httputil.bean.HttpConfig;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zidong.wang on 2016/1/5.
 */
@Service
public class SpiderService {

    public static final Logger logger = Logger.getLogger(SpiderService.class);

    @Autowired
    private UEService ueService;


    public void beginSpiderSite(){
        HttpUtil httpUtil = HttpUtil.getInstance();
        String url = "http://gagalulu.wang";
        String ua = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
        String refer = "http://www.baidu.com";
        HttpConfig httpConfig = new HttpConfig();
        httpConfig.setCharset("utf-8");
        httpConfig.setRefer(refer);
        httpConfig.setUa(ua);
        String content = httpUtil.getContentByUrlAndConfig(url,httpConfig);
        logger.info(content);
    }

    public AjaxModel spiderJianShu(String url,DailyModel tempModel){
        AjaxModel ajaxModel = new AjaxModel();

        logger.info(url);
        HttpUtil httpUtil = HttpUtil.getInstance();
        String content = httpUtil.getContentByUrl(url);

        parseJianShuContent(content,tempModel);

        ajaxModel.setStatus(ErrorCode.SUCCESS);
        return ajaxModel;
    }

    private void parseJianShuContent(String content,DailyModel dailyModel){
        Document doc = Jsoup.parse(content);
        Elements elements = doc.getElementsByClass("show-content");
        Element conEle = elements.get(0);
//        logger.info(conEle.html());
        String dailyContent = conEle.html();
        dailyContent = dailyContent.replace("data-original-src","src");
        Element titleEle = doc.getElementsByClass("title").get(0);
        String title = titleEle.html();
        Element desEle = doc.getElementsByAttributeValue("name","description").get(0);
        String description = desEle.attr("content");

        dailyModel.setTitle(title);
        dailyModel.setContent(dailyContent);
        dailyModel.setCreateTime(new Date());
        dailyModel.setDescription(description);

        ueService.saveOrUpdate(dailyModel);
    }

    public AjaxModel spiderCsdn(String url,DailyModel tempModel){
        AjaxModel ajaxModel = new AjaxModel();
        logger.info(url);
        HttpUtil httpUtil = HttpUtil.getInstance();
        String content = httpUtil.getContentByUrl(url);
        parseCSDNContent(content,tempModel);
        ajaxModel.setStatus(ErrorCode.SUCCESS);
        return ajaxModel;
    }

    private void parseCSDNContent(String content,DailyModel dailyModel){
        Document doc = Jsoup.parse(content);
        Element conEle = doc.getElementById("article_content");
        conEle.getElementsByTag("img").attr("style","");
//        conEle.getElementsByClass("pre-numbering").remove();
        conEle.getElementsByTag("script").remove();
//        logger.info(conEle.html());
        String dailyContent = conEle.html();

        Element titleEle = doc.getElementsByTag("title").get(0);
        String title = titleEle.html();
//        logger.info(title);
        Element desEle = doc.getElementsByAttributeValue("name","description").get(0);
        String description = desEle.attr("content");
//        logger.info(description);

        dailyModel.setTitle(title);
        dailyModel.setContent(dailyContent);
        dailyModel.setCreateTime(new Date());
        dailyModel.setDescription(description);

        ueService.saveOrUpdate(dailyModel);
    }

}
