package com.prince.myproj.blog.models;

/**
 * Created by zidong.wang on 2015/12/24.
 */
public class VideoModel {
    private Long id;
    private String title;
    private String desc;
    private String src;
    private String cate;
    private String createTime;
    private String cover;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getCate() {
        return cate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getSrc() {
        return src;
    }

    public String getTitle() {
        return title;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
