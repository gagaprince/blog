package com.prince.myproj.blog.models;

import java.util.Date;

/**
 * Created by gagaprince on 15-12-20.
 */
public class DailyModel {
    private Long id;
    private String title;
    private String content;
    private String pic;
    private String cate;
    private String bigCate;
    private Date createTime;
    private String createTimeStr;

    public Date getCreateTime() {
        return createTime;
    }

    public Long getId() {
        return id;
    }

    public String getCate() {
        return cate;
    }

    public String getContent() {
        return content;
    }

    public String getPic() {
        return pic;
    }

    public String getTitle() {
        return title;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getBigCate() {
        return bigCate;
    }

    public void setBigCate(String bigCate) {
        this.bigCate = bigCate;
    }
}
