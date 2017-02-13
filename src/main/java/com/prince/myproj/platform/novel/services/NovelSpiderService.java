package com.prince.myproj.platform.novel.services;

import com.prince.myproj.platform.common.models.AjaxModel;
import com.prince.myproj.platform.common.statics.Constant;
import com.prince.myproj.platform.common.statics.ErrorCode;
import com.prince.myproj.platform.novel.models.NovelModel;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * Created by zidong.wang on 2017/2/13.
 */
@Service
public class NovelSpiderService {
    private static final Logger logger =  Logger.getLogger(NovelSpiderService.class);

    private HttpUtil httpUtil = HttpUtil.getInstance();

    public AjaxModel spiderNovelByPno(String pno){
        AjaxModel ajaxModel = new AjaxModel();

        String url = Constant.zw37Url;

        String htmlContent = httpUtil.getContentByUrl(url);

        Document doc = Jsoup.parse(htmlContent);
        Element contentEle = doc.getElementById("content");
        if(contentEle!=null){
            NovelModel novelModel = new NovelModel();
            String contentStr = contentEle.html();
            contentStr = contentStr.replace("<br>\n<br>","<br/>");
            logger.info(contentStr);
            novelModel.setContent(contentStr);
            ajaxModel.setStatus(ErrorCode.SUCCESS);
            ajaxModel.setData(novelModel);
        }else{
            ajaxModel.setStatus(ErrorCode.NETWORK_ERROR);
        }
        return ajaxModel;
    }

}
