package com.prince.myproj.platform.common.statics;

/**
 * Created by zidong.wang on 2017/2/13.
 */
public enum ErrorCode {
    SUCCESS("成功",0),
    NETWORK_ERROR("网络服务错误",1),
    NOT_FIND_ERROR("没有找到对应的条目",2),
    NOT_NOVEL_ID_ERROR("没有传novelid",3),
    NOT_NOVEL_ERROR("没有查到对应的小说",4),
    NOT_NOVEL_CHAPTER_ERROR("没有查到对应的章节",5),
    NOT_SEARCHKEY_FOUND("没有搜索词",6),
    NOT_LOAD_PHOTO("没有正确下载图片",7)
    ;
    private String des;
    private int code;
    // 构造方法
    private ErrorCode(String des, int code) {
        this.des = des;
        this.code = code;
    }
    public String getDes(){
        return des;
    }

    public int getCode() {
        return code;
    }
}
