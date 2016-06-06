package com.prince.myproj.shares.services;

import com.prince.myproj.shares.dao.SharesDao;
import com.prince.myproj.shares.dao.SharesHistoryDao;
import com.prince.myproj.shares.models.ShareConfig;
import com.prince.myproj.shares.models.SharesModel;
import com.prince.myproj.shares.models.SharesSingleModel;
import com.prince.myproj.util.DateUtil;
import com.prince.myproj.util.MailService;
import com.prince.myproj.util.bean.Mail;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zidong.wang on 2016/5/17.
 */
@Service
public class SharesMailService {

    public static final Logger logger = Logger.getLogger(SharesMailService.class);

    @Autowired
    private ShareConfig config;

    @Autowired
    private SharesDao sharesDao;
    @Autowired
    private SharesHistoryDao sharesHistoryDao;

    @Autowired
    private DateUtil dateUtil;

    //发送分析邮件
    public void sendMail(){
        Mail mail = new Mail();
        mail.setSubject(getSubject());
        mail.setContent(getMailContent());
        mail.setFrom(config.getFromUser());
        mail.setTo(config.getToUser());
        mail.setSmtp(config.getStmp());
        mail.setUsername(config.getMailUserName());
        mail.setPassword(config.getMailPassword());
        MailService.send(mail);
    }

    /**
     * 收盘前发送预测邮件
     */
    public void sendMailPre(){

    }

    private String getSubject(){
        String dateStr = dateUtil.getNowDate();
        return dateStr+"股票市场分析";
    }



    private String getMailContent(){

        StringBuffer sb = new StringBuffer();

        HttpUtil httpUtil = HttpUtil.getInstance();
        String content = httpUtil.getContentByUrl("http://localhost:9999/shares/today");
        sb.append(content);

        return sb.toString();
    }
}
