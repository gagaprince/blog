package com.prince.myproj.util;

import com.prince.myproj.util.bean.Mail;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Created by zidong.wang on 2016/5/31.
 */
public class MailService {
    public static final Logger logger = Logger.getLogger(MailService.class);

    private MimeMessage mimeMsg; //MIME邮件对象
    private Session session; //邮件会话对象
    private Properties props; //系统属性
    private boolean needAuth = false; //smtp是否需要认证
    private Multipart mp; //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
    private Mail mail;

    public MailService (Mail mail){
        this.mail = mail;
        setSmtpHost(mail.getSmtp());
        createMimeMessage();
        try {
            initMailService();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void initMailService() throws MessagingException {
        Mail mail = this.mail;
        setNeedAuth(true);
        mimeMsg.setSubject(mail.getSubject());
        setBody(mail.getContent());
        mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo()));
        mimeMsg.setFrom(new InternetAddress(mail.getFrom())); //设置发信人

    }

    public void setBody(String mailBody) throws MessagingException {
        BodyPart bp = new MimeBodyPart();
        bp.setContent(""+mailBody,"text/html;charset=GBK");
        mp.addBodyPart(bp);
    }

    private void setNeedAuth(boolean need){
        if(props==null){
            props = System.getProperties();
        }
        props.put("mail.smtp.auth",need+"");
    }

    public boolean createMimeMessage()
    {
        try {
            logger.info("准备获取邮件会话对象！");
            session = Session.getDefaultInstance(props,null); //获得邮件会话对象
        }
        catch(Exception e){
            logger.error("获取邮件会话对象时发生错误！" + e);
            return false;
        }

        logger.info("准备创建MIME邮件对象！");
        try {
            mimeMsg = new MimeMessage(session); //创建MIME邮件对象
            mp = new MimeMultipart();

            return true;
        } catch(Exception e){
            logger.error("创建MIME邮件对象失败！" + e);
            return false;
        }
    }

    /**
     * 发送邮件
     */
    public boolean sendOut()
    {
        try{
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            logger.info("正在发送邮件....");

            Session mailSession = Session.getInstance(props,null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String)props.get("mail.smtp.host"),mail.getUsername(),mail.getPassword());
            transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO));
//            transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.CC));
//            transport.send(mimeMsg);

            logger.info("发送邮件成功！");
            transport.close();

            return true;
        } catch(Exception e) {
            logger.error("邮件发送失败！" + e);
            return false;
        }
    }

    public static boolean send(Mail mail){
        MailService theMail = new MailService(mail);
        return theMail.sendOut();
    }

    public void setSmtpHost(String hostName){
        if(props==null){
            props = System.getProperties();
        }
        props.put("mail.smtp.host",hostName);
    }

}
