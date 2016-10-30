package com.prince.myproj.blog.models;

/**
 * Created by zidong.wang on 2016/8/12.
 */
public class DataModel {
    String uid;
    String nfrom;
    String page;
    public DataModel(String uid,String nfrom,String page){
        this.uid=uid;
        this.nfrom=nfrom;
        this.page=page;
    }

    public String getNfrom() {
        return nfrom;
    }

    public String getPage() {
        return page;
    }

    public String getUid() {
        return uid;
    }

    public void setNfrom(String nfrom) {
        this.nfrom = nfrom;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
