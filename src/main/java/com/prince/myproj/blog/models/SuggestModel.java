package com.prince.myproj.blog.models;

/**
 * Created by gagaprince on 15-12-19.
 */
public class SuggestModel {
    private int id;
    //背景
    private String bg;
    //谨言的title
    private String title;

    //谨言的内容
    private String content;

    public String getBg() {
        return bg;
    }

    public String getTitle() {
        return title;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

