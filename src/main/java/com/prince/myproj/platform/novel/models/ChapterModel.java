package com.prince.myproj.platform.novel.models;

import java.util.Date;

/**
 * Created by zidong.wang on 2017/2/15.
 */
public class ChapterModel {
    private long id;
    private long novelId;
    private Date createTime;
    private String sourceUrl;
    private long chapter;
    private String name;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSourceUrl() {
        return sourceUrl;
    }

    public long getChapter() {
        return chapter;
    }

    public long getId() {
        return id;
    }

    public long getNovelId() {
        return novelId;
    }


    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setChapter(long chapter) {
        this.chapter = chapter;
    }

    public void setNovelId(long novelId) {
        this.novelId = novelId;
    }
}
