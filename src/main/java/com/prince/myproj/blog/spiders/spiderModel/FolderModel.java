package com.prince.myproj.blog.spiders.spiderModel;

import java.util.List;

/**
 * Created by gagaprince on 15-12-27.
 */
public class FolderModel {
    private String detailUrl;
    private String title;
    private String cover;
    private List<String> jpgs;
    private Integer photoFolderId;

    public Integer getPhotoFolderId() {
        return photoFolderId;
    }

    public void setPhotoFolderId(Integer photoFolderId) {
        this.photoFolderId = photoFolderId;
    }

    public List<String> getJpgs() {
        return jpgs;
    }

    public String getCover() {
        return cover;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public void setJpgs(List<String> jpgs) {
        this.jpgs = jpgs;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
