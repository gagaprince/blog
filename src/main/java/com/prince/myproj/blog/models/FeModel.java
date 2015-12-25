package com.prince.myproj.blog.models;

import java.util.Date;

/**
 * Created by zidong.wang on 2015/12/25.
 */
public class FeModel {
    private long id;
    private String name;
    private String desc;
    private String cover;
    private String url;
    private String downloadUrl;
    private Date createTime;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDesc() {
        return desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public long getId() {
        return id;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
