package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/5/17.
 */
public class ShareConfig {
    private String historyUrl;
    private String realTimeUrl;
    private String shareCodeUrl;
    private String shareTablePath;
    private String shareInterfacePath;
    private String historyAddUrl;
    private String mailUserName;
    private String mailPassword;
    private String stmp;
    private String toUser;
    private String fromUser;

    private String divisionAllUrl;//��˾�б�
    private String divisionSearchUrl;//���ݹ�˾������˾�µ�division
    private String lhbListUrl;//������list��ַ
    private String lhbDetailUrl;//�����������ַ

    public String getLhbDetailUrl() {
        return lhbDetailUrl;
    }

    public String getLhbListUrl() {
        return lhbListUrl;
    }

    public void setLhbDetailUrl(String lhbDetailUrl) {
        this.lhbDetailUrl = lhbDetailUrl;
    }

    public void setLhbListUrl(String lhbListUrl) {
        this.lhbListUrl = lhbListUrl;
    }

    public String getDivisionAllUrl() {
        return divisionAllUrl;
    }

    public String getDivisionSearchUrl() {
        return divisionSearchUrl;
    }

    public void setDivisionAllUrl(String divisionAllUrl) {
        this.divisionAllUrl = divisionAllUrl;
    }

    public void setDivisionSearchUrl(String divisionSearchUrl) {
        this.divisionSearchUrl = divisionSearchUrl;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getStmp() {
        return stmp;
    }

    public String getToUser() {
        return toUser;
    }

    public void setStmp(String stmp) {
        this.stmp = stmp;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public String getMailUserName() {
        return mailUserName;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public void setMailUserName(String mailUserName) {
        this.mailUserName = mailUserName;
    }

    public String getHistoryAddUrl() {
        return historyAddUrl;
    }

    public void setHistoryAddUrl(String historyAddUrl) {
        this.historyAddUrl = historyAddUrl;
    }

    public String getShareTablePath() {
        return shareTablePath;
    }

    public void setShareTablePath(String shareTablePath) {
        this.shareTablePath = shareTablePath;
    }

    public String getHistoryUrl() {
        return historyUrl;
    }

    public String getRealTimeUrl() {
        return realTimeUrl;
    }

    public String getShareCodeUrl() {
        return shareCodeUrl;
    }

    public void setShareCodeUrl(String shareCodeUrl) {
        this.shareCodeUrl = shareCodeUrl;
    }

    public void setHistoryUrl(String historyUrl) {
        this.historyUrl = historyUrl;
    }

    public void setRealTimeUrl(String realTimeUrl) {
        this.realTimeUrl = realTimeUrl;
    }

    public String getShareInterfacePath() {
        return shareInterfacePath;
    }

    public void setShareInterfacePath(String shareInterfacePath) {
        this.shareInterfacePath = shareInterfacePath;
    }
}
