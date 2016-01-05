package com.prince.myproj.blog.services;

import com.prince.util.httputil.HttpUtil;
import com.prince.util.httputil.bean.HttpConfig;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by zidong.wang on 2016/1/5.
 */
@Service
public class SpiderService {

    public static final Logger logger = Logger.getLogger(SpiderService.class);


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
}
