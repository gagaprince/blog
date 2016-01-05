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
    private String tag;
    private String description;
    private Date createTime;
    private String createTimeStr;

    public DailyModel(){
        this.tag="前端技术,html5,游戏开发,技术教程,技术博客,个人空间,后端技术,java,mysql,Spring,前端框架,前端技术分享,游戏引擎,html5游戏引擎,特效";
        this.description = "互联网行业需要的技能大概分为，前端技术，html5，css，js等，后端技术，J2EE,SSH,linux,mysql等，有了这两样就可以做出属于自己的网站，还会有不菲的收入，快来和我一起学习吧。这里是技术的聚集地，记录我在平时遇到的各种问题，和解决方案。也希望能和大家广泛交流协作。";
    }

    public String getDescription() {
        return description;
    }

    public String getTag() {
        return tag;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

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
