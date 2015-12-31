package com.prince.myproj.blog.models;

import java.util.Date;

/**
 * Created by zidong.wang on 2015/12/22.
 */
public class PhotoFolderModel {
    private Integer id;
    private String name;
    private String cover;
    private Date createTime;
    private String desc;
    private long rank;

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getId() {
        return id;
    }

    public String getCover() {
        return cover;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }
}
