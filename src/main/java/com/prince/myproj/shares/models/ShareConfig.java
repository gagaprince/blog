package com.prince.myproj.shares.models;

/**
 * Created by zidong.wang on 2016/5/17.
 */
public class ShareConfig {
    private String historyUrl;
    private String realTimeUrl;
    private String shareCodeUrl;
    private String shareTablePath;
    private String historyAddUrl;

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
}
