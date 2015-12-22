package com.prince.myproj.blog.models;

import java.util.Date;

/**
 * Created by zidong.wang on 2015/12/22.
 */
public class PhotoModel {
    private Long id;
    private String name;
    private String picUrl;
    private String desc;
    private Date createTime;
    private Integer photoFolderId;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getPhotoFolderId() {
        return photoFolderId;
    }

    public Long getId() {
        return id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setPhotoFolderId(Integer photoFolderId) {
        this.photoFolderId = photoFolderId;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
