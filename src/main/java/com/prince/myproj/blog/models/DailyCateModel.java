package com.prince.myproj.blog.models;

public class DailyCateModel {
    private String cateName;
    private int count;

    public int getCount() {
        return count;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
