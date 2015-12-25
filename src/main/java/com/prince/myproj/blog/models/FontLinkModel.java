package com.prince.myproj.blog.models;

/**
 * Created by zidong.wang on 2015/12/25.
 */
public class FontLinkModel {
    private int id;
    private String title;
    private String link;
    private String cover;
    private String bigCate;
    private String smallcate;
    private String createTime;
    private long rank;

    public String getCover() {
        return cover;
    }

    public int getId() {
        return id;
    }

    public long getRank() {
        return rank;
    }

    public String getBigcate() {
        return bigCate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getLink() {
        return link;
    }

    public String getSmallcate() {
        return smallcate;
    }

    public String getTitle() {
        return title;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public void setBigcate(String bigcate) {
        this.bigCate = bigcate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setSmallcate(String smallcate) {
        this.smallcate = smallcate;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
