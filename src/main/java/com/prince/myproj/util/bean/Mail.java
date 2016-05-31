package com.prince.myproj.util.bean;

/**
 * Created by zidong.wang on 2016/5/31.
 */
public class Mail {
    String smtp;
    String from;
    String to;
    String copyto;
    String subject;
    String content;
    String username;
    String password;
    String filename;

    public String getContent() {
        return content;
    }

    public String getCopyto() {
        return copyto;
    }

    public String getFilename() {
        return filename;
    }

    public String getFrom() {
        return from;
    }

    public String getPassword() {
        return password;
    }

    public String getSmtp() {
        return smtp;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

    public String getUsername() {
        return username;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCopyto(String copyto) {
        this.copyto = copyto;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
